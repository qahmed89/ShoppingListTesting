package com.example.shoppinglisttesting

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourseComparerTest {

    private lateinit var resourseComparer: ResourseComparer

    @Before
    fun setup() {
        resourseComparer = ResourseComparer()
    }

    @Test
    fun StringResourceSameAsGivenString_returnTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourseComparer.isEqual(context, R.string.name, "Ahmed")
        assertThat(result).isTrue()
    }

    @Test
    fun StringResourceDifferentAsGivenString_returnFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourseComparer.isEqual(context, R.string.name, "ahmed")
        assertThat(result).isFalse()
    }
}