package com.example.explorenepal.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.explorenepal.DatabaseHelper;
import com.example.explorenepal.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddData extends Fragment {

    private static final int REQUEST_IMAGE_PICKER = 1;
    private ImageView imageViewUpload;
    private byte[] imageBytes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_data, container, false);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        EditText editTextName = view.findViewById(R.id.edit_text_name);
        Spinner spinnerCategory = view.findViewById(R.id.spinner_category);

        String[] category = {"Historical Sites", "Natural Attractions", "Cultural", "Hotel", "Restaurant"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, category);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        Button buttonUploadImage = view.findViewById(R.id.btn_upload_image);
        EditText editTextPhone = view.findViewById(R.id.edit_text_phone);
        EditText editTextDescription = view.findViewById(R.id.edit_text_description);
        Button saveBtn = view.findViewById(R.id.save_btn);
        imageViewUpload = view.findViewById(R.id.image_view);

        buttonUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String description = editTextDescription.getText().toString();
                String category = spinnerCategory.getSelectedItem().toString();

                boolean success = databaseHelper.addInsert(name, category, imageBytes, phone, description); // Call the method on the instance
                if (success) {
                    Toast.makeText(getContext(), "Data saved successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to save data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    // Method to open image picker
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImageUri);
                imageViewUpload.setImageBitmap(bitmap);
                imageBytes = getImageBytes(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to convert the selected image to byte array
    private byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        return stream.toByteArray();
    }
}
