package com.example.vision.scene;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vision.R;
import com.example.vision.profile.ProfileLayoutViewModel;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;


public class Scene extends Fragment {
    TextView alert;

    ImageButton takePicture;
    ImageButton speech;
    SpeechRecognizer speechRecognizer;


    SceneViewModel sceneViewModel;
    Observer<String> alertObserver =new Observer<String>() {
        @Override
        public void onChanged(String s) {
            alert.setText(s);

        }
    };
    private static final int REQUEST_RECORED_AUDIO_PERMISSION = 1;
    private static final int REQUEST_CAMERA_PERMISSION = 0;
    PreviewView previewView;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    ImageCapture imageCapture;

    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        cameraProvider.unbindAll();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        Preview preview = new Preview.Builder().build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        // Initialize ImageCapture once within bindPreview()
        imageCapture = new ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build();

        cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scene, container, false);
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    Toast.makeText(getActivity(),"Camera permission is required",Toast.LENGTH_SHORT).show();

                }
            });
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        speech = view.findViewById(R.id.speech);
        takePicture = view.findViewById(R.id.take_picture);
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(getActivity());



        final Intent speechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Toast.makeText(getActivity(), "Listening..", Toast.LENGTH_SHORT ).show();

            }

            @Override
            public void onBeginningOfSpeech() {

                Toast.makeText(getActivity(), "Listening..", Toast.LENGTH_SHORT ).show();

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(data!=null && !data.isEmpty()){
                    if(Objects.equals(data.get(0), "Take Picture")){
                        Toast.makeText(getActivity(), "Taking Picture..", Toast.LENGTH_SHORT ).show();

                        extracted();
                    }

                }


            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        speech.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{ android.Manifest.permission.RECORD_AUDIO}, REQUEST_RECORED_AUDIO_PERMISSION);
                } else {
                    // Permission already granted, start camera operations
                }



                speechRecognizer.startListening(speechRecognizerIntent);
            }

        });
        sceneViewModel = new ViewModelProvider(getActivity()).get(SceneViewModel.class);
        alert =  view.findViewById(R.id.alert);
        sceneViewModel.getAlert().observe(getViewLifecycleOwner(), alertObserver);
        if (ContextCompat.checkSelfPermission(
                getActivity(), android.Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                getActivity(), android.Manifest.permission.CAMERA)) {

        } else {

            requestPermissionLauncher.launch(
                    Manifest.permission.CAMERA);
        }

        if (ContextCompat.checkSelfPermission(
                getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(
                getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

        } else {

            requestPermissionLauncher.launch(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (getActivity() != null) {

            if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{ android.Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            } else {
                // Permission already granted, start camera operations
            }

            cameraProviderFuture = ProcessCameraProvider.getInstance(getActivity());
            previewView = view.findViewById(R.id.previewView);
            cameraProviderFuture.addListener(() -> {
                try {
                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    if (cameraProvider != null) {
                        bindPreview(cameraProvider);
                    } else {
                        Log.e("CameraError", "Error: CameraProvider is null");
                        // Handle the error appropriately, like displaying a message to the user
                    }
                } catch (ExecutionException | InterruptedException e) {
                    Log.e("CameraError", "Error initializing CameraX", e);
                    // Handle the error appropriately
                }
            }, ContextCompat.getMainExecutor(getActivity()));
        } else {
            Log.e("CameraError", "Error: getActivity() returned null");
            // Handle the error appropriately
        }

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extracted();
            }
        });



    }

    private void extracted() {
        SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String timestamp = timestampFormat.format(new Date());

        // Create a file in the app's dedicated directory (e.g., external files dir)
        File imagesDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imageFile = new File(imagesDir, "image_" + timestamp + ".jpg");
        ImageCapture.OutputFileOptions outputFileOptions =
                new ImageCapture.OutputFileOptions.Builder(imageFile).build();

        imageCapture.takePicture(outputFileOptions,  ContextCompat.getMainExecutor(getActivity()),
                new ImageCapture.OnImageSavedCallback() {
                    @Override
                    public void onImageSaved(ImageCapture.OutputFileResults outputFileResults) {
                        Uri savedUri = outputFileResults.getSavedUri();
                        if (savedUri != null) {
                            File imageFile = new File(savedUri.getPath()); // Get the imagefile
                            Log.println(Log.ASSERT, "I am taking a picture ", savedUri.getPath());
                            sceneViewModel.predict(imageFile);

                        } else {
                            // Handle the case where savedUri is null (e.g., image saving failed)
                        }
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Log.println(Log.ASSERT, "I am taking a picture ",exception.toString());

                    }

                }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sceneViewModel.getAlert().removeObserver(alertObserver);
    }
}