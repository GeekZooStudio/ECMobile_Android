package com.insthub.ecmobile.adapter;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.insthub.BeeFramework.adapter.BeeBaseAdapter;
import com.insthub.ecmobile.R;
import com.insthub.ecmobile.protocol.BONUS;

import java.util.ArrayList;
import java.util.List;

public class C6_RedEnvelopeAdapter extends BeeBaseAdapter{

    private int selectedPosition = -1;// 选中的位置

    public C6_RedEnvelopeAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    public class RedPacketsHolder extends BeeCellHolder
    {
        public TextView redCodeTextView;
        public TextView changeMoneyTextView;
        public ImageView redPacketCheck;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView)
    {
        RedPacketsHolder cellHolder = new RedPacketsHolder();
        cellHolder.redCodeTextView = (TextView)cellView.findViewById(R.id.red_code);
        cellHolder.changeMoneyTextView = (TextView)cellView.findViewById(R.id.change_money);
        cellHolder.redPacketCheck      = (ImageView)cellView.findViewById(R.id.red_packet_check);
        return cellHolder;
    }

    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h)
    {
        BONUS bonus = (BONUS)dataList.get(position);
        RedPacketsHolder holder = (RedPacketsHolder)h;
        holder.redCodeTextView.setText(bonus.bonus_id);
        holder.changeMoneyTextView.setText(bonus.bonus_money_formated);
        if (selectedPosition -1 == position)
        {
            holder.redPacketCheck.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.redPacketCheck.setVisibility(View.GONE);
        }
        return null;
    }

    @Override
    public View createCellView()
    {
        return LayoutInflater.from(mContext).inflate(R.layout.c6_red_envelope_cell, null);
    }
}
