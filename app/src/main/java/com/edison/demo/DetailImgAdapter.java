package com.edison.demo;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * auther 任晓光
 * on 2018/11/9 0009 14:00
 */
public class DetailImgAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public DetailImgAdapter() {
        super(R.layout.item_img);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageUtil.displayPortrait(mContext, item, (ImageView) helper.getView(R.id.iv_img));
    }
}
