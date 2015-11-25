package com.example.student.criminalintent;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("ALL")
public class CrimeCameraFragment extends Fragment
{
    private static final String TAG = "CrimeCameraFragment";

    private Camera mCamera;
    private SurfaceView mSurfaceView;

    private Camera.Size getBestSupportedSize(List<Camera.Size> sizes, int width, int height)
    {
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width * bestSize.height;
        for (Camera.Size s : sizes)
        {
            int area = s.width * s.height;
            if (area > largestArea)
            {
                bestSize = s;
                largestArea = area;
            }
        }
        return bestSize;
    }

    @Override
    @SuppressWarnings("deprecation")
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_crime_camera, parent, false);

        Button takePictureButton = (Button)v.findViewById(R.id.crime_camera_takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                getActivity().finish();
            }
        });
        mSurfaceView = (SurfaceView)v.findViewById(R.id.crime_camera_surfaceView);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        holder.addCallback(new SurfaceHolder.Callback()
        {
            public void surfaceCreated(SurfaceHolder holder)
            {
                try
                {
                    if (mCamera != null)
                    {
                        mCamera.setPreviewDisplay(holder);
                    }
                } catch (IOException exception)
                {
                    Log.e(TAG, "Error setting up preview display", exception);
                }
            }

            public void surfaceDestroyed(SurfaceHolder holder)
            {
                if (mCamera != null)
                {
                    mCamera.stopPreview();
                }
            }

            public void surfaceChanged(SurfaceHolder holder, int format, int w, int h)
            {
                if (mCamera == null) return;

                Camera.Parameters parameters = mCamera.getParameters();
                Camera.Size s = getBestSupportedSize(parameters.getSupportedPreviewSizes(), w, h);
                parameters.setPreviewSize(s.width, s.height);
                mCamera.setParameters(parameters);
                try
                {
                    mCamera.startPreview();
                } catch (Exception e)
                {
                    Log.e(TAG, "Could not start preview", e);
                    mCamera.release();
                    mCamera = null;
                }
            }
        });

        return v;
    }

    @TargetApi(9)
    @Override
    public void onResume()
    {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
        {
            mCamera = Camera.open(0);
        } else
        {
            mCamera = Camera.open();
        }
    }

    // Releases the Camera resource.
    @Override
    public void onPause()
    {
        super.onPause();

        if (mCamera != null)
        {
            mCamera.release();
            mCamera = null;
        }
    }
}