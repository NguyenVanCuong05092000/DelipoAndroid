package jp.adapter.delipo.test.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import jp.adapter.delipo.R;
import jp.adapter.delipo.test.listener.OnClickProductListener;
import jp.adapter.delipo.test.model.Product;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private Context context;
    private OnClickProductListener listener;

    public ProductAdapter(List<Product> productList, Context context, OnClickProductListener listener) {
        this.productList = productList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_product, parent, false);

        final ProductViewHolder holder = new ProductViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (listener != null)
                        listener.onClickProduct(getItem(holder.getAdapterPosition()), holder.getAdapterPosition());
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });


        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        /** Set Value*/
        final Product product = productList.get(position);

        final String category = product.getmCategory();
        String detail = product.getmDetail();
        final int image = product.getmImage();

        holder.tvCategory.setText(category);
        holder.tvDetail.setText(detail);
        Picasso.get().load(image).into(holder.iv);

    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    Product getItem(int position) {
        return productList.get(position);
    }

    /**
     * Create ViewHolder
     */
    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv;
        private TextView tvCategory;
        private TextView tvDetail;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.imgThumb);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvDetail = itemView.findViewById(R.id.tvDetail);
        }
    }
}