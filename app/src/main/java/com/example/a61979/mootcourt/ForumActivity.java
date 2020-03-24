package com.example.a61979.mootcourt;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a61979.mootcourt.domain.forumbean.forumBean;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ForumActivity extends AppCompatActivity {
    //private BottomSheetDialog dialog;
    private ListView listview;
    private List<forumBean> forumdatas;
    public int[] images = new int[]{R.drawable.user_logo, R.drawable.user_other, R.drawable.user_1, R.drawable.user_2
            , R.drawable.user_3, R.drawable.user_4, R.drawable.user_5
    };
    public String[] title = new String[]{"刘淑兰反应内蒙古杭锦后旗交警大队非法限制人生自由不让看病的事实",
            "扬州市邗江区长钱锋方巷镇长书记村霸鱼肉百姓", "北京协和医院的司法保护伞不除，百姓难安，危害无穷！"
            , "北京市高级人民法院再次受理本人再审申请，可喜可贺可悲！", "邓炯汉、覃勇、秦春林、韦志民、合伙损害中国共产党形象破坏司法公正！"
            , "你打人骨折(鉴定轻伤),天津鸿顺里派出所于俊涛所长为你撑起保护伞,安(转载)", "天津鸿顺里派出所于俊涛所长: 你保护他们”故意伤害,”隐避些别公开呀"
    };
    public String[] username = new String[]{"法律尊严正义", "春茔匠", "小富即安W3", "人做诗与诗做人", "讲真理说真话",
            "山岩石子", "漂泊玉树"};
    private MyAdapter adapter;
    private FloatingActionButton bt_addforum;
    private Dialog dialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        forumdatas = new ArrayList<forumBean>();
        for (int i = 0; i < title.length; i++) {

            //添加数据
            forumBean m = new forumBean("", "", "水光潋滟晴方好，山色空蒙雨亦奇。\n欲把西湖比西子，淡抹浓妆总相宜。");
            m.setImageID(images[i]);
            m.setTitle(title[i]);
            m.setViewsCnt(String.valueOf(200 - i * 17));
            m.setReplyCnt(String.valueOf(170 - i * 17));
            m.setUsername("作者：" + username[i]);
            forumdatas.add(m);

        }


        listview = findViewById(R.id.main_list_view);
        bt_addforum = (FloatingActionButton) findViewById(R.id.bt_addforum);
        bt_addforum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showforumDialog();
            }
        });
        adapter = new MyAdapter();
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new MyOnItemClickonListener());
    }

    /*
     * Listview适配器
     * */
    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return forumdatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = View.inflate(ForumActivity.this, R.layout.forum_item_listview, null);
                viewHolder.headimg = (CircleImageView) convertView.findViewById(R.id.headImg);
                viewHolder.title = (TextView) convertView.findViewById(R.id.forumDisplayTitle);
                viewHolder.postUserName = (TextView) convertView.findViewById(R.id.postUserName);
                viewHolder.viewsCnt = (TextView) convertView.findViewById(R.id.viewsCnt);
                viewHolder.replyCnt = (TextView) convertView.findViewById(R.id.replyCnt);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            forumBean m = forumdatas.get(position);
            viewHolder.headimg.setImageResource(m.getImageID());
            viewHolder.title.setText(m.getTitle());
            viewHolder.viewsCnt.setText(m.getViewsCnt());
            viewHolder.replyCnt.setText(m.getReplyCnt());
            viewHolder.postUserName.setText(m.getUsername());
            return convertView;
        }
    }

    static class ViewHolder {
        CircleImageView headimg;
        TextView title;
        TextView postUserName;
        TextView viewsCnt;
        TextView replyCnt;
    }

    /*
     *设置listview监听事件
     * */
    private class MyOnItemClickonListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(ForumActivity.this, ForumDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("forum", forumdatas.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
    /*
     * 展示发帖对话窗
     * */

    private void showforumDialog() {
        //dialog = new BottomSheetDialog(MainActivity.this);
        dialog2 = new Dialog(ForumActivity.this);
        dialog2.setCanceledOnTouchOutside(true);
        View commentView = LayoutInflater.from(ForumActivity.this).inflate(R.layout.forum_dialog_layout, null);
        final EditText forumtitle = (EditText) commentView.findViewById(R.id.dialog_forum_title);
        final EditText forumct = (EditText) commentView.findViewById(R.id.dialog_forum_ct);
        final Button bt_forum = (Button) commentView.findViewById(R.id.dialog_forum_bt);
        final Button bt_cancel = (Button) commentView.findViewById(R.id.dialog_cancel_bt);
        //设置成全屏
        Window window = dialog2.getWindow();
        //window.setWindowAnimations(R.style.dialog);
        window.setContentView(commentView);
        window.setBackgroundDrawable(new BitmapDrawable());
        //设置alterdialog全屏
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog2.getWindow().getAttributes();
        lp.height = (int) (display.getHeight()); //设置宽度
        lp.width = (int) (display.getWidth()); //设置宽度
        dialog2.getWindow().setAttributes(lp);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        bt_forum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String forumTitle = forumtitle.getText().toString().trim();
                String forumContent = forumct.getText().toString().trim();
                if (!TextUtils.isEmpty(forumContent) && !TextUtils.isEmpty(forumTitle)) {

                    //commentOnWork(commentContent);
                    ForumActivity.this.dialog2.dismiss();
                    forumBean detailBean = new forumBean("法律至上", forumTitle, forumContent);
                    addTheforumData(detailBean);
                    Toast.makeText(ForumActivity.this, "发帖成功", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(ForumActivity.this, "标题/帖子内容不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
        this.dialog2.show();
    }

    /*
     * 添加帖子数据
     * */
    public void addTheforumData(forumBean forumDetailBean) {
        if (forumDetailBean != null) {

            forumdatas.add(forumDetailBean);
            adapter.notifyDataSetChanged();
        } else {
            throw new IllegalArgumentException("评论数据为空!");
        }

    }

}
