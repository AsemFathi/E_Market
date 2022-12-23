package com.egtactile.e_market;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackHolder> implements RecyclerViewInterface {
    Context context;
    DatabaseReference reference;
    List<Feedback> feedbackList;
    FirebaseUser user;
    private final RecyclerViewInterface recyclerViewInterface;

    public FeedbackAdapter(Context context, List<Feedback> feedbackList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.feedbackList = feedbackList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public FeedbackHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeedbackHolder(LayoutInflater.from(context).inflate(R.layout.feedback_recycler_view, parent , false) , recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackHolder holder, int position) {
        reference = FirebaseDatabase.getInstance().getReference().child("Feedback");
        user = FirebaseAuth.getInstance().getCurrentUser();
        /*reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String Email = dataSnapshot.getKey();
                    if (Email.toLowerCase().equals(user.getEmail()
                            .replaceAll("@gmail.com" , "")
                            .toLowerCase()))
                    {
                        holder.Feedback.setText(item);
                        holder.ratingBar.setRating((Float) dataSnapshot.child("Rate").getValue());


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
*/

        holder.Feedback.setText(feedbackList.get(position).getFeedback());
        holder.ratingBar.setRating( feedbackList.get(position).getRate());
        holder.Username.setText(feedbackList.get(position).getUsername());


    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    @Override
    public void onItemClick(int pos) {

    }
}
