package jp.adapter.delipo.adapter;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import jp.adapter.delipo.R;
import jp.adapter.delipo.entity.ProductEntity;
import jp.adapter.delipo.listener.OnClickProductListener;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    private ArrayList<ProductEntity> arrayList;
    private Context context;
    private OnClickProductListener listener;
    private int width, padding;
    private int categoryW, categoryH;

    public AdapterProduct(Context context, int width, int padding, OnClickProductListener listener) {
        this.context = context;
        this.width = width;
        this.padding = padding;
        this.listener = listener;
        categoryW = (int) (width * 10f / 100);
        categoryH = (int) (categoryW * 100f / 100);
    }

    public void updateData(ArrayList<ProductEntity> arrayList) {
        try {
            this.arrayList = new ArrayList<>();
            if (arrayList != null && !arrayList.isEmpty())
                this.arrayList.addAll(arrayList);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMoreData(ArrayList<ProductEntity> arrayList) {
        try {
            if (arrayList == null || arrayList.isEmpty())
                return;

            int posStart = getItemCount();
            if (posStart == 0) {
                updateData(arrayList);
                return;
            }
            this.arrayList.addAll(arrayList);
            notifyItemRangeInserted(posStart, getItemCount() - posStart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_search, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d("SearchFragment", "onClick: Click Product");
                    if (listener != null)
                        listener.onClickProduct(getItem(holder.getAdapterPosition()));
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductEntity product = getItem(position);
        if (!TextUtils.isEmpty(product.saasesName))
            holder.tvSaasesName.setText(product.displayCreateAt + "\n" + product.saasesName);

        if (product.imgThumb != null)
            Glide.with(context).load(product.imgThumb.getUrl()).into(holder.imgThumb);

        holder.imgStar1.setImageResource(R.drawable.ic_iv_star);
        holder.imgStar2.setImageResource(R.drawable.ic_iv_star);
        holder.imgStar3.setImageResource(R.drawable.ic_iv_star);
        holder.imgStar4.setImageResource(R.drawable.ic_iv_star);
        holder.imgStar5.setImageResource(R.drawable.ic_iv_star);
    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    ProductEntity getItem(int position) {
        return arrayList.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSaasesName;
        private ImageView imgThumb;
        private ImageView imgStar1;
        private ImageView imgStar2;
        private ImageView imgStar3;
        private ImageView imgStar4;
        private ImageView imgStar5;
        private LinearLayout llStar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSaasesName = itemView.findViewById(R.id.tvSaasesName);

            imgThumb = itemView.findViewById(R.id.imgThumb);
            imgThumb.getLayoutParams().width = width;
            imgThumb.getLayoutParams().height = width;

            imgStar1 = itemView.findViewById(R.id.imgStar1);
            imgStar1.getLayoutParams().width = categoryW;
            imgStar1.getLayoutParams().height = categoryH;

            imgStar2 = itemView.findViewById(R.id.imgStar2);
            imgStar2.getLayoutParams().width = categoryW;
            imgStar2.getLayoutParams().height = categoryH;

            imgStar3 = itemView.findViewById(R.id.imgStar3);
            imgStar3.getLayoutParams().width = categoryW;
            imgStar3.getLayoutParams().height = categoryH;

            imgStar4 = itemView.findViewById(R.id.imgStar4);
            imgStar4.getLayoutParams().width = categoryW;
            imgStar4.getLayoutParams().height = categoryH;

            imgStar5 = itemView.findViewById(R.id.imgStar5);
            imgStar5.getLayoutParams().width = categoryW;
            imgStar5.getLayoutParams().height = categoryH;

            llStar = itemView.findViewById(R.id.llStar);

            itemView.setPadding(0, 0, padding, padding);
        }
    }
}
