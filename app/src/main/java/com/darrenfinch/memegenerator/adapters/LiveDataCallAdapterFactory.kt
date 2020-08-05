package com.darrenfinch.memegenerator.adapters

import androidx.lifecycle.LiveData
import retrofit2.CallAdapter
import retrofit2.Retrofit

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * http://innotech.vn
 * Created by Huynh Van Duc on 11/1/2018.
 */
class LiveDataCallAdapterFactory private constructor() : CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit?
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        check(returnType is ParameterizedType) {
            ("LiveData return type must be parameterized"
                    + " as LiveData<Foo> or LiveData<? extends Foo>")
        }
        val innerType: Type = getParameterUpperBound(
            0,
            returnType as ParameterizedType
        )
        if (getRawType(innerType) != Response::class.java) {
            // Generic type is not Response<T>. Use it for body-only adapter.
            return BodyCallAdapter<Any>(innerType)
        }

        // Generic type is Response<T>. Extract T and create the Response version of the adapter.
        check(innerType is ParameterizedType) {
            ("Response must be parameterized"
                    + " as Response<Foo> or Response<? extends Foo>")
        }
        val responseType: Type =
            getParameterUpperBound(0, innerType as ParameterizedType)
        return ResponseCallAdapter<Any>(responseType)
    }

    private class BodyCallAdapter<R> internal constructor(private val responseType: Type) :
        CallAdapter<R, LiveData<R>?> {
        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<R>): LiveData<R> {
            return object : LiveData<R>() {
                override fun onInactive() {
                    call.cancel()
                }

                init {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>?, response: Response<R>) {
                            if (response.isSuccessful()) {
                                postValue(response.body())
                            } else {
                                postValue(null)
                            }
                        }

                        override fun onFailure(call: Call<R>?, t: Throwable?) {
                            postValue(null)
                        }
                    })
                }
            }
        }

    }

    private class ResponseCallAdapter<R> internal constructor(private val responseType: Type) :
        CallAdapter<R, LiveData<Response<R>?>?> {
        override fun responseType(): Type {
            return responseType
        }

        override fun adapt(call: Call<R>): LiveData<Response<R>?>? {
            return object : LiveData<Response<R>?>() {
                override fun onInactive() {
                    call.cancel()
                }

                init {
                    call.enqueue(object : Callback<R> {
                        override fun onResponse(call: Call<R>?, response: Response<R>?) {
                            value = response
                        }

                        override fun onFailure(call: Call<R>?, t: Throwable?) {
                            value = null
                        }
                    })
                }
            }
        }

    }

    companion object {
        fun create(): LiveDataCallAdapterFactory {
            return LiveDataCallAdapterFactory()
        }
    }
}