package cn.jiguang.share.demo.uitool;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cloud on 17/1/13.
 */

class ShareBoardMenuHelper {
    private static String TAG = ShareBoardMenuHelper.class.getSimpleName();
    private ShareBoardConfig mShareBoardConfig;

    public ShareBoardMenuHelper(ShareBoardConfig shareBoardConfig) {
        this.mShareBoardConfig = shareBoardConfig;
    }

    public List<SnsPlatform[][]> formatPageData(List<SnsPlatform> menuData) {
        int pageSize = this.mShareBoardConfig.mMenuColumnNum * 2;
        int menuSize = menuData.size();
        ArrayList result = new ArrayList();
        int lastPageRowNum;
        if(menuSize < this.mShareBoardConfig.mMenuColumnNum) {
            SnsPlatform[][] var15 = new SnsPlatform[1][menuSize];

            for(lastPageRowNum = 0; lastPageRowNum < menuData.size(); ++lastPageRowNum) {
                var15[0][lastPageRowNum] = (SnsPlatform)menuData.get(lastPageRowNum);
            }

            result.add(var15);
            return result;
        } else {
            int pageCount = menuSize / pageSize;
            lastPageRowNum = -1;
            int lastPageMenuSize = menuSize % pageSize;
            if(lastPageMenuSize != 0) {
                lastPageRowNum = lastPageMenuSize / this.mShareBoardConfig.mMenuColumnNum + (lastPageMenuSize % this.mShareBoardConfig.mMenuColumnNum != 0?1:0);
                ++pageCount;
            }

            int menuCount;
            for(menuCount = 0; menuCount < pageCount; ++menuCount) {
                int page;
                if(menuCount == pageCount - 1 && lastPageRowNum != -1) {
                    page = lastPageRowNum;
                } else {
                    page = 2;
                }

                SnsPlatform[][] i = new SnsPlatform[page][this.mShareBoardConfig.mMenuColumnNum];
                result.add(i);
            }

            menuCount = 0;

            for(int var16 = 0; var16 < result.size(); ++var16) {
                SnsPlatform[][] var17 = (SnsPlatform[][])result.get(var16);
                int rowNum = var17.length;

                for(int row = 0; row < rowNum; ++row) {
                    SnsPlatform[] column = var17[row];

                    for(int col = 0; col < column.length; ++col) {
                        if(menuCount < menuSize) {
                            column[col] = (SnsPlatform)menuData.get(menuCount);
                        }

                        ++menuCount;
                    }
                }
            }

            return result;
        }
    }

    public View createPageLayout(Context context, SnsPlatform[][] pageData) {
        LinearLayout pageLayout = new LinearLayout(context);
        pageLayout.setOrientation(LinearLayout.VERTICAL);
        pageLayout.setGravity(Gravity.TOP);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        pageLayout.setLayoutParams(layoutParams);

        for(int i = 0; i < pageData.length; ++i) {
            SnsPlatform[] rowData = pageData[i];
            View rowView = this.createRowLayout(context, rowData, i != 0);
            pageLayout.addView(rowView);
        }

        return pageLayout;
    }

    private View createRowLayout(Context context, SnsPlatform[] rowData, boolean isHasTopMargin) {
        LinearLayout rowLayout = new LinearLayout(context);
        rowLayout.setOrientation(LinearLayout.HORIZONTAL);
        rowLayout.setGravity(1);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        if(isHasTopMargin) {
            layoutParams.topMargin = this.dip2px(context, 20.0F);
        }

        rowLayout.setLayoutParams(layoutParams);

        for(int i = 0; i < rowData.length; ++i) {
            View btn = this.createBtnView(context, rowData[i]);
            rowLayout.addView(btn);
        }

        return rowLayout;
    }

    private View createBtnView(Context context, final SnsPlatform snsPlatform) {
        LinearLayout container = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2);
        layoutParams.weight = 1.0F;
        container.setLayoutParams(layoutParams);
        container.setGravity(17);
        if(snsPlatform != null) {
            ResContainer resContainer = ResContainer.get(context);
            View btn = LayoutInflater.from(context).inflate(resContainer.layout("socialize_share_menu_item"), (ViewGroup)null);
            SocializeImageView imageView = (SocializeImageView)btn.findViewById(resContainer.id("socialize_image_view"));
            TextView btnText = (TextView)btn.findViewById(resContainer.id("socialize_text_view"));
            if(this.mShareBoardConfig.mMenuBgColor != 0 && this.mShareBoardConfig.mMenuBgShape != ShareBoardConfig.BG_SHAPE_NONE) {
                imageView.setBackgroundColor(this.mShareBoardConfig.mMenuBgColor, this.mShareBoardConfig.mMenuBgPressedColor);
                imageView.setBackgroundShape(this.mShareBoardConfig.mMenuBgShape, this.mShareBoardConfig.mMenuBgShapeAngle);
            } else {
                imageView.setPadding(0, 0, 0, 0);
            }

            if(this.mShareBoardConfig.mMenuIconPressedColor != 0) {
                imageView.setPressedColor(this.mShareBoardConfig.mMenuIconPressedColor);
            }

            imageView.setBackgroundShapeStroke(this.mShareBoardConfig.mMenuBgShapeStrokeWidth, this.mShareBoardConfig.mMenuBgShapeStrokeColor);

            String text = "";

            try {
                text = ResContainer.getString(context, snsPlatform.mShowWord);
            } catch (Exception var15) {
                String platform = snsPlatform.mPlatform;
                Log.d(TAG, "fetch btn str failed,platform is:" + platform);
            }

            if(!TextUtils.isEmpty(text)) {
                btnText.setText(ResContainer.getString(context, snsPlatform.mShowWord));
            }

            btnText.setGravity(17);
            int rId = 0;

            try {
                rId = ResContainer.getResourceId(context, "drawable", snsPlatform.mIcon);
            } catch (Exception var14) {
                String targetPlat = snsPlatform.mPlatform;
                Log.d(TAG, "fetch icon id failed,platform is:" + targetPlat);
            }

            if(rId != 0) {
                imageView.setImageResource(rId);
            }

            if(this.mShareBoardConfig.mMenuTextColor != 0) {
                btnText.setTextColor(this.mShareBoardConfig.mMenuTextColor);
            }

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String platform = snsPlatform.mPlatform;
                    if(ShareBoardMenuHelper.this.mShareBoardConfig != null && ShareBoardMenuHelper.this.mShareBoardConfig.getShareBoardlistener() != null) {
                        ShareBoardMenuHelper.this.mShareBoardConfig.getShareBoardlistener().onclick(snsPlatform, platform);
                    }

                }
            });
            container.addView(btn);
        }

        return container;
    }

    private int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }
}
