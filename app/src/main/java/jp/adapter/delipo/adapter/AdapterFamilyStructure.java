package jp.adapter.delipo.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import jp.adapter.delipo.R;
import jp.adapter.delipo.entity.FamilyStructureEntity;
import jp.adapter.delipo.utils.DebugHelper;
import jp.adapter.delipo.view.CustomImageButton;

public class AdapterFamilyStructure extends RecyclerView.Adapter<AdapterFamilyStructure.ViewHolder> {
    private static final String TAG = "AdapterFamilyStructure";
    private ArrayList<FamilyStructureEntity> listFamilyStructure;
    private int wightLabelValue;
    private int wightButtonItem;
    private int heightButtonItem;

    public AdapterFamilyStructure(int wightLabelValue, int wightButtonItem, int heightButtonItem, ArrayList<FamilyStructureEntity> listFamilyStructure) {
        this.listFamilyStructure = listFamilyStructure;
        this.wightLabelValue = wightLabelValue;
        this.wightButtonItem = wightButtonItem;
        this.heightButtonItem = heightButtonItem;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_family_structure_new, parent, false);
        ViewHolder holder = new ViewHolder(view, wightLabelValue, wightButtonItem, heightButtonItem);
        handleClickButton(holder);
        return holder;
    }

    private FamilyStructureEntity getItem(int position){
        return listFamilyStructure.get(position);
    }

    private void handleClickButton(ViewHolder holder) {
        //MEN-
        holder.btnDecreaseBlue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                FamilyStructureEntity structureEntity = getItem(holder.getAdapterPosition());
                int currentValue = structureEntity.getValueMen();
                if (currentValue > 0) {
                    int decValue = currentValue - 1;
                    setBackgroundValue(holder.tvValueMen, decValue > 0);
                    holder.tvValueMen.setText(decValue + "");
                    structureEntity.setValueMen(decValue);

                }
            }
        });
        //MEN+
        holder.btnIncreaseBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FamilyStructureEntity structureEntity = getItem(holder.getAdapterPosition());
                int currentValue = structureEntity.getValueMen();
                if (currentValue < 99) {
                    int decValue = currentValue + 1;
                    setBackgroundValue(holder.tvValueMen, decValue > 0);
                    holder.tvValueMen.setText(decValue + "");
                    structureEntity.setValueMen(decValue);
                }
            }
        });

        //WOMEN-
        holder.btnDecreasePink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FamilyStructureEntity structureEntity = getItem(holder.getAdapterPosition());
                int currentValue = structureEntity.getValueWomen();
                if (currentValue > 0) {
                    int decValue = currentValue - 1;
                    setBackgroundValue(holder.tvValueWoMen, decValue > 0);
                    holder.tvValueWoMen.setText(decValue + "");
                    structureEntity.setValueWomen(decValue);
                }
            }
        });
        //WOMEN+
        holder.btnIncreasePink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FamilyStructureEntity structureEntity = getItem(holder.getAdapterPosition());
                int currentValue = structureEntity.getValueWomen();
                if (currentValue < 99) {
                    int decValue = currentValue + 1;
                    setBackgroundValue(holder.tvValueWoMen, decValue > 0);
                    holder.tvValueWoMen.setText(decValue + "");
                    structureEntity.setValueWomen(decValue);
                }
            }
        });
    }

    @SuppressLint({"SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FamilyStructureEntity structureEntity = listFamilyStructure.get(position);
        holder.tvTitle.setText(structureEntity.getTitle());
        holder.tvValueMen.setText(structureEntity.getValueMen() + "");
        holder.tvValueWoMen.setText(structureEntity.getValueWomen() + "");
    }

    private void setBackgroundValue(TextView value, boolean isHaveValue) {
        if (isHaveValue) {
            value.setBackgroundResource(R.drawable.bg_value_family_structure_clicked);
            value.setTextColor(Color.WHITE);
        } else {
            value.setBackgroundResource(R.drawable.bg_value_family_structure);
            value.setTextColor(Color.BLACK);

        }
    }

    @Override
    public int getItemCount() {
        DebugHelper.Log(TAG, "getItemCount: " + (listFamilyStructure == null ? 0 : listFamilyStructure.size()));
        return listFamilyStructure == null ? 0 : listFamilyStructure.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView btnDecreaseBlue, btnIncreaseBlue, btnDecreasePink, btnIncreasePink;
        private TextView tvValueWoMen, tvValueMen, tvTitle;

        public ViewHolder(@NonNull View itemView, int wightLabelValue, int wightButtonItem, int heightButtonItem) {
            super(itemView);
            btnDecreaseBlue = itemView.findViewById(R.id.btnDecreaseBlue);
            btnIncreaseBlue = itemView.findViewById(R.id.btnIncreaseBlue);
            btnDecreasePink = itemView.findViewById(R.id.btnDecreasePink);
            btnIncreasePink = itemView.findViewById(R.id.btnIncreasePink);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvValueWoMen = itemView.findViewById(R.id.tvValueWoMen);
            tvValueMen = itemView.findViewById(R.id.tvValueMen);

            DebugHelper.Log(TAG, "wightButtonItem: " + wightButtonItem + "-heightButtonItem: " + heightButtonItem);
            btnDecreaseBlue.getLayoutParams().width = wightButtonItem;
            btnDecreaseBlue.getLayoutParams().height = heightButtonItem;

            btnDecreasePink.getLayoutParams().width = wightButtonItem;
            btnDecreasePink.getLayoutParams().height = heightButtonItem;

            btnIncreaseBlue.getLayoutParams().width = wightButtonItem;
            btnIncreaseBlue.getLayoutParams().height = heightButtonItem;

            btnIncreasePink.getLayoutParams().width = wightButtonItem;
            btnIncreasePink.getLayoutParams().height = heightButtonItem;

            tvValueMen.getLayoutParams().width = wightLabelValue;
            tvValueMen.getLayoutParams().height = heightButtonItem;

            tvValueWoMen.getLayoutParams().width = wightLabelValue;
            tvValueWoMen.getLayoutParams().height = heightButtonItem;
        }
    }
}
