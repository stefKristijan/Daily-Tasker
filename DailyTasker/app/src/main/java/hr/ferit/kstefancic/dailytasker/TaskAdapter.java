package hr.ferit.kstefancic.dailytasker;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Kristijan on 11.4.2017..
 */

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    ArrayList<Task> mTasks;
    Context context;

    public TaskAdapter(ArrayList<Task> tasks, Context context){
        this.mTasks=tasks;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View taskView = inflater.inflate(R.layout.task_item,parent,false);
        ViewHolder taskViewHolder = new ViewHolder(taskView);
        return taskViewHolder;
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Task task = this.mTasks.get(position);
        holder.tvCategory.setText(task.getCategory());
        holder.tvDate.setText(task.getDate());
        holder.tvTitle.setText(task.getTitle());
        holder.tvDescription.setText(task.getDescription());
        holder.rlMain.setBackground(context.getDrawable(task.getPriority()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                TaskDBHelper.getInstance(context).deleteTask(mTasks.get(position));
                delete(position,holder);
                return true;
            }
        });
    }

    public void insert(Task task){
        this.mTasks.add(task);
        this.notifyDataSetChanged();
    }

    public void delete(int position, final ViewHolder holder){
        this.mTasks.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.mTasks.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

         TextView tvTitle, tvDescription, tvDate, tvCategory;
        ScrollView scrollCategory;
        RelativeLayout rlMain;

        ViewHolder(View itemView) {
            super(itemView);
            this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            this.rlMain= (RelativeLayout) itemView.findViewById(R.id.rlMain);
            this.tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            this.tvDescription= (TextView) itemView.findViewById(R.id.tvDescription);
            this.tvDate= (TextView) itemView.findViewById(R.id.tvDate);
            this.scrollCategory = (ScrollView) itemView.findViewById(R.id.scrollCategory);
                this.scrollCategory.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }

            });
        }
    }
}
