/*
 * Copyright (C) 2017 Moez Bhatti <moez.bhatti@gmail.com>
 *
 * This file is part of QKSMS.
 *
 * QKSMS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QKSMS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QKSMS.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.moez.QKSMS.feature.compose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.moez.QKSMS.R
import com.moez.QKSMS.common.base.QkThemedActivity
import com.moez.QKSMS.model.Attachment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.container_activity.*
import java.net.URLDecoder

class ComposeActivity : QkThemedActivity() {

    private lateinit var router: Router

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_activity)

        router = Conductor.attachRouter(this, container, savedInstanceState)
        if (!router.hasRootController()) {
            val query = intent.extras?.getString("query") ?: ""
            val threadId = intent.extras?.getLong("threadId") ?: 0L
            val address = intent.data?.let {
                val data = it.toString()
                val scheme = it.scheme ?: ""
                when {
                    scheme.startsWith("smsto") -> data.replace("smsto:", "")
                    scheme.startsWith("mmsto") -> data.replace("mmsto:", "")
                    scheme.startsWith("sms") -> data.replace("sms:", "")
                    scheme.startsWith("mms") -> data.replace("mms:", "")
                    else -> ""
                }
            }?.let { if (it.contains('%')) URLDecoder.decode(it, "UTF-8") else it } ?: ""// The dialer app on Oreo sends a URL encoded string, make sure to decode it
            val sharedText = intent.extras?.getString(Intent.EXTRA_TEXT) ?: ""
            val sharedAttachments = intent.getParcelableArrayListExtra<Uri>(Intent.EXTRA_STREAM)
                    ?.plus(intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM))
                    ?.map { Attachment(it) }
                    ?: listOf()

            router.setRoot(RouterTransaction.with(ComposeController(query, threadId, address, sharedText, sharedAttachments)))
        }
    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }

}