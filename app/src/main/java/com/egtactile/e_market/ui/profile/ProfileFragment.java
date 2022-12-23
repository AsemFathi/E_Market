package com.egtactile.e_market.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.egtactile.e_market.EditAdmin;
import com.egtactile.e_market.R;
import com.egtactile.e_market.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    TextView name,mail,phone;
    DatabaseReference databaseReference;
    FirebaseUser userdata;
    CircleImageView imageView;
    String email;
    private FragmentProfileBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        name= root.findViewById(R.id.nameview);
        mail= root.findViewById(R.id.mailview);
        phone = root.findViewById(R.id.phoneview);
        imageView = root.findViewById(R.id.profile_image);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        userdata = FirebaseAuth.getInstance().getCurrentUser();

        email = userdata.getEmail();
        email = email.replaceAll("@gmail.com","");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot indx:snapshot.getChildren()){
                    if (indx.getKey().equals(email)){
                        /*
                        newUser.child("Full Name").setValue(name);
                            newUser.child("Email").setValue(email);
                            newUser.child("Phone").setValue(phone);
                        * */
                        String image = indx.child("Image").getValue().toString();
                        Picasso.get()
                                .load(image)
                                .into(imageView);
                        name.setText(indx.child("Full Name").getValue().toString());
                        mail.setText(indx.child("Email").getValue().toString());
                        phone.setText(indx.child("Phone").getValue().toString());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
