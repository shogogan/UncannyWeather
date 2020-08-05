package br.com.shogogan.uncannyweather.utils

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import java.nio.channels.AsynchronousServerSocketChannel

abstract class BaseEspressoTesting {
    lateinit var mockWebServer: MockWebServer

    @Before
    open fun setUp(){
        mockWebServer = MockWebServer()
        mockWebServer.start(MOCK_PORT)
    }

    @After
    open fun tearDown(){
        mockWebServer.close()
    }

    companion object{
        const val MOCK_PORT = 7007
        const val MOCK_URL = "http://localhost:7007"
    }
}