package com.example.aninterface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<ModelStudent> studentList;
    private Context context;
    private OnStudentClickListener onStudentClickListener;

    // Corrected spelling for the interface name
    public interface OnStudentClickListener {
        void onStudentClick(ModelStudent student);
    }

    // Constructor to initialize studentList, context, and listener
    public StudentAdapter(List<ModelStudent> studentList, Context context, OnStudentClickListener onStudentClickListener) {
        this.studentList = studentList;
        this.context = context;
        this.onStudentClickListener = onStudentClickListener;
    }

    // Creates new views (invoked by the layout manager)
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    // Replaces the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        ModelStudent student = studentList.get(position);
        holder.studentNameTextView.setText(student.getStudentName());
        holder.fatherNameTextView.setText(student.getFatherName());
        holder.fatherCNICTextView.setText(student.getFatherCNIC());
        holder.regNoTextview.setText(student.getRegistrationNo());

        // Setting onClickListener for each item
        holder.itemView.setOnClickListener(v -> onStudentClickListener.onStudentClick(student));
    }

    // Returns the size of the studentList (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return studentList.size();
    }

    // Inner ViewHolder class to represent the UI elements of each item
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView studentNameTextView;
        TextView fatherNameTextView;
        TextView fatherCNICTextView;
        TextView regNoTextview;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.tv_name);
            fatherNameTextView = itemView.findViewById(R.id.tv_father_name);
            fatherCNICTextView = itemView.findViewById(R.id.tv_father_CNIC);
            regNoTextview = itemView.findViewById(R.id.tv_reg_no);
        }
    }
}
