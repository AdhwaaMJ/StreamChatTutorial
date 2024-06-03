package com.project.chattutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import io.getstream.video.android.compose.permission.LaunchCallPermissions
import io.getstream.video.android.compose.theme.VideoTheme
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.core.GEO
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.core.call.state.FlipCamera
import io.getstream.video.android.core.call.state.LeaveCall
import io.getstream.video.android.core.call.state.ToggleCamera
import io.getstream.video.android.core.call.state.ToggleMicrophone
import io.getstream.video.android.model.User

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apiKey = "mmhfdzb5evj2"
        val userToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiQXNhampfVmVudHJlc3MiLCJpc3MiOiJodHRwczovL3Byb250by5nZXRzdHJlYW0uaW8iLCJzdWIiOiJ1c2VyL0FzYWpqX1ZlbnRyZXNzIiwiaWF0IjoxNzE3NDE4NzAwLCJleHAiOjE3MTgwMjM1MDV9.DKj9apDnHzWcZAkebd80R8uwCpiCef_sTpDmSJRw23w"
        val userId = "Asajj_Ventress"
        val callId = "W63n8VYGTTwc"

        val user = User(
            id = userId,
            name = "Tutorial",
            image = "https://bit.ly/2TIt8NR",
            role = "admin",
        )

        val client = StreamVideoBuilder(
            context = applicationContext,
            apiKey = apiKey, // demo API key
            geo = GEO.GlobalEdgeNetwork,
            user = user,
            token = userToken,
        ).build()

        setContent {
            val call = client.call(type = "default", id = callId)
            LaunchCallPermissions(call = call) {
                call.join(create = true)
            }

            VideoTheme {
                CallContent(
                    modifier = Modifier.fillMaxSize(),
                    call = call,
                    enableInPictureInPicture = true,
                    onBackPressed = { finish() },
                    onCallAction = { callAction ->
                        when (callAction) {
                            is FlipCamera -> call.camera.flip()
                            is ToggleCamera -> call.camera.setEnabled(callAction.isEnabled)
                            is ToggleMicrophone -> call.microphone.setEnabled(callAction.isEnabled)
                            is LeaveCall -> finish()
                            else -> Unit
                        }
                    },
                )
            }
        }
    }
}