package cn.iwgang.familiarrecyclerviewdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerViewOnScrollListener;

public class LinearLayoutActivity extends AppCompatActivity {
    private FamiliarRecyclerView mRecyclerView;
    private List<String> mDatas;
    private MyAdapter mAdapter;

    private boolean isVertical = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_layout_linear);

        isVertical = getIntent().getBooleanExtra("isVertical", true);

        if (isVertical) {
            setContentView(R.layout.act_layout_linear_ver);
        } else {
            setContentView(R.layout.act_layout_linear_hor);
        }

        mDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mDatas.add("item:" + i);
        }

        mRecyclerView = (FamiliarRecyclerView) findViewById(R.id.mRecyclerView);

        // Item Click and Item Long Click
        mRecyclerView.setOnItemClickListener(new FamiliarRecyclerView.OnItemClickListener() {
            @Override
            public void onItemClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                Log.i("wg", "onItemClick = " + familiarRecyclerView + " _ " + view + " _ " + position);
                Toast.makeText(LinearLayoutActivity.this, "onItemClick = " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerView.setOnItemLongClickListener(new FamiliarRecyclerView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(FamiliarRecyclerView familiarRecyclerView, View view, int position) {
                Log.i("wg", "onItemLongClick = " + familiarRecyclerView + " _ " + view + " _ " + position);
                Toast.makeText(LinearLayoutActivity.this, "onItemLongClick = " + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        // set LayoutManager in code
//        if (isVertical) {
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        } else {
//            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        }

        mRecyclerView.addOnScrollListener(new FamiliarRecyclerViewOnScrollListener(mRecyclerView.getLayoutManager()) {
            @Override
            public void onScrolledToTop() {
//                Log.i("wg", "onScrolledToTop ...");
            }

            @Override
            public void onScrolledToBottom() {
//                Log.i("wg", "onScrolledToBottom ...");
            }
        });

        // ItemAnimator
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mRecyclerView.setHeaderDividersEnabled(true);
//        mRecyclerView.setFooterDividersEnabled(true);

        // head view
        mRecyclerView.addHeaderView(HeaderAndFooterViewUtil.getHeadView(this, isVertical, 0xFFFF5000, "Head View 1"));
//        mRecyclerView.addHeaderView(HeaderAndFooterViewUtil.getHeadView(this, isVertical, Color.BLUE, "Head View 2"));

        // footer view
        mRecyclerView.addFooterView(HeaderAndFooterViewUtil.getFooterView(this, isVertical, 0xFF778899, "Foot View 1"));
//        mRecyclerView.addFooterView(HeaderAndFooterViewUtil.getFooterView(this, isVertical, Color.RED, "Foot View 2"));

        // empty view
        mRecyclerView.setEmptyView(findViewById(R.id.tv_empty), true);

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            int layoutId = isVertical ? R.layout.item_view_linear : R.layout.item_view_linear_hor;
            return new MyViewHolder(LayoutInflater.from(LinearLayoutActivity.this).inflate(layoutId, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.mTvTxt.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTvTxt;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTvTxt = (TextView) itemView.findViewById(R.id.tv_txt);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int notifyPos;
        switch (item.getItemId()) {
            case R.id.id_action_add:
                notifyPos = mDatas.size();

                mDatas.add("new add item:" + notifyPos);
                mAdapter.notifyItemInserted(notifyPos);
                break;
            case R.id.id_action_delete:
                if (mDatas.isEmpty()) return true;

                notifyPos = mDatas.size() - 1;

                mDatas.remove(notifyPos);
                mAdapter.notifyItemRemoved(notifyPos);
                break;
        }
        return true;
    }
}
