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

import android.net.Uri
import androidx.annotation.StringRes
import androidx.core.view.inputmethod.InputContentInfoCompat
import com.moez.QKSMS.common.base.QkView
import com.moez.QKSMS.model.Attachment
import com.moez.QKSMS.model.Contact
import com.moez.QKSMS.model.Message
import io.reactivex.Observable
import io.reactivex.subjects.Subject

interface ComposeView : QkView<ComposeState> {

    fun activityVisible(): Observable<Boolean>
    fun queryChanges(): Observable<CharSequence>
    fun queryBackspaces(): Observable<*>
    fun queryEditorActions(): Observable<Int>
    fun chipSelected(): Observable<Contact>
    fun chipDeleted(): Observable<Contact>
    fun menuReady(): Observable<*>
    fun optionItemSelected(): Observable<Int>
    fun sendAsGroupToggled(): Observable<*>
    fun messageClicks(): Subject<Message>
    fun messagesSelected(): Observable<List<Long>>
    fun sendingCancelled(): Subject<Message>
    fun attachmentDeleted(): Subject<Attachment>
    fun textChanged(): Observable<CharSequence>
    fun attachClicks(): Observable<*>
    fun cameraClicks(): Observable<*>
    fun galleryClicks(): Observable<*>
    fun scheduleClicks(): Observable<*>
    fun attachmentSelected(): Observable<Uri>
    fun inputContentSelected(): Observable<InputContentInfoCompat>
    fun scheduleTimeSelected(): Observable<Long>
    fun scheduleCancelled(): Observable<*>
    fun simChanged(): Observable<*>
    fun sendClicks(): Observable<*>
    fun qksmsPlusClicks(): Subject<*>
    fun backPresses(): Observable<*>

    fun clearSelection()
    fun showDetails(details: String)
    fun requestStoragePermission()
    fun requestCamera()
    fun requestGallery()
    fun requestDatePicker()
    fun setDraft(draft: String)
    fun scrollToMessage(id: Long)
    fun showQksmsPlusSnackbar(@StringRes message: Int)

}