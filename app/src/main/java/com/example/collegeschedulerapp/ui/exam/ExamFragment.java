package com.example.collegeschedulerapp.ui.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegeschedulerapp.MainActivity;
import com.example.collegeschedulerapp.R;
import com.example.collegeschedulerapp.databinding.FragmentExamsBinding;

public class ExamFragment extends Fragment {

    private FragmentExamsBinding binding;
    EditText etFirstName, etLastName, etFavFood;
    Button btnAdd,btnView,button;
    DatabaseHelper mydb;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ExamViewModel slideshowViewModel =
                new ViewModelProvider(this).get(ExamViewModel.class);

        binding = FragmentExamsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        //final TextView textView = binding.textSlideshow;
        //slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        etFavFood = (EditText) root.findViewById(R.id.etFavFood);
        etFirstName = (EditText) root.findViewById(R.id.etFirstName);
        etLastName = (EditText) root.findViewById(R.id.etLastName);
        btnAdd = (Button) root.findViewById(R.id.btnAdd);
        btnView = (Button) root.findViewById(R.id.btnView);

        button = (Button) root.findViewById(R.id.button);

        mydb = new DatabaseHelper(getActivity().getApplicationContext());
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),ViewListContents.class);
//                Toast.makeText(requireContext(),"after..1",Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fName = etFirstName.getText().toString();
                String lName = etLastName.getText().toString();
                String fFood = etFavFood.getText().toString();
                if(fName.length() != 0 && lName.length() != 0 && fFood.length() != 0){
                    AddData(fName,lName, fFood);
                    etFavFood.setText("");
                    etLastName.setText("");
                    etFirstName.setText("");
                }else{
                    Toast.makeText(getContext(),"You must put something in the text field!",Toast.LENGTH_LONG).show();
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetDatabase(root);
            }
        });

        return root;
    }
    public void AddData(String firstName,String lastName, String favFood ){
        boolean insertData = mydb.addData(firstName,lastName,favFood);

        if(insertData==true){
            Toast.makeText(getContext(),"Successfully Entered Data!",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }
    public void resetDatabase(View view) {
        mydb.deleteAllData();
        // You may want to refresh your UI or perform any other necessary actions after resetting
        Toast.makeText(getContext(), "Database reset", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}