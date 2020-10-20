package jp.adapter.delipo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import jp.adapter.delipo.R;
import jp.adapter.delipo.entity.ImageEntity;

public class AdapterVPImgProduct extends PagerAdapter {
    private Context mContext;
    private LayoutInflater inflater;
    private ArrayList<ImageEntity> arrListPhoto;

    public AdapterVPImgProduct(Context context, ArrayList<ImageEntity> arrayList) {
        inflater = LayoutInflater.from(context);
        mContext = context;
        arrListPhoto = arrayList;
    }

    @Override
    public int getCount() {
        return arrListPhoto != null ? arrListPhoto.size() : 0;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View vPage = inflater.inflate(R.layout.item_vp_img_product, container, false);
        ImageView imageView = vPage.findViewById(R.id.image);
        Glide.with(mContext).load(arrListPhoto.get(position).getUrl()).into(imageView);
        container.addView(vPage);
        return vPage;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
