package com.orion.salesman._class;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orion.salesman.R;
import com.orion.salesman._infor._fragment._object.TreeMsg;

import java.util.List;

/**
 * Created by maidinh on 31/10/2016.
 */
public class UserTree extends LinearLayout {
    Context context;
    TreeMsg treeMsg;
    TextView tvMsg;
    LinearLayout lnRoot;
    int mgLeft;
    int idIMG;
    Button img;

    public UserTree(Context context, TreeMsg treeMsg, int mgLeft, int idIMG) {
        super(context);
        this.context = context;
        this.treeMsg = treeMsg;
        this.mgLeft = mgLeft;
        this.idIMG = idIMG;
        init(context, treeMsg, mgLeft, idIMG);
    }


    private void init(Context context, TreeMsg treeMsg, int mgLeft, int idIMG) {
        LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.tree_msg_layout, this, true);
        lnRoot = (LinearLayout) findViewById(R.id.lnRoot);
        img = (Button) findViewById(R.id.img);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(mgLeft, 0, 0, 0);
        lnRoot.setLayoutParams(params);
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        tvMsg.setText(treeMsg.getV2());
        img.setBackgroundResource(idIMG);
    }
}
