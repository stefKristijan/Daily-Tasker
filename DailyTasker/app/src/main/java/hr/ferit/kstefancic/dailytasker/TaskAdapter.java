package hr.ferit.kstefancic.dailytasker;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kristijan on 11.4.2017..
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    ArrayList<Task> mTasks;

    public TaskAdapter(ArrayList<Task> tasks){
        this.mTasks=tasks;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View taskView = inflater.inflate(R.layout.task_item,parent,false);
        ViewHolder taskViewHolder = new ViewHolder(taskView);
        return taskViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Task task = this.mTasks.get(position);
        holder.tvCategory.setText(task.getCategory());
        holder.tvDate.setText(task.getDate());
        holder.tvTitle.setText(task.getTitle());
        holder.tvDescription.setText(task.getDescription());
        holder.vPriority.setBackgroundColor(task.getPriority());
    }

    public void insert(Task task){
        this.mTasks.add(task);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.mTasks.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle, tvDescription, tvDate, tvCategory;
        public View vPriority;

        public ViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            this.vPriority = itemView.findViewById(R.id.vPriority);
            this.tvDescription= (TextView) itemView.findViewById(R.id.tvDescription);
            this.tvDate= (TextView) itemView.findViewById(R.id.tvDate);
        }
    }
}
