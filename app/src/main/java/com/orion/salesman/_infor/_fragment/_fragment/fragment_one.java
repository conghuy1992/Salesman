package com.orion.salesman._infor._fragment._fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orion.salesman.MainActivity;
import com.orion.salesman.R;
import com.orion.salesman._adaper.ViewPagerAdapter;
import com.orion.salesman._app.MyApplication;
import com.orion.salesman._class.CustomViewPager;
import com.orion.salesman._class.HttpRequest;
import com.orion.salesman._infor._fragment._adapter.AdapterNews;
import com.orion.salesman._infor._fragment._object.ListOneNews;
import com.orion.salesman._infor._fragment._object.News;
import com.orion.salesman._infor._fragment._object.OneNew;
import com.orion.salesman._interface.IF_2;
import com.orion.salesman._pref.PrefManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maidinh on 4/10/2016.
 */
public class fragment_one extends Fragment {
    private String TAG = "fragment_one";
    private TabLayout tabLayout;
    private CustomViewPager viewPager;
    private String DATE;
    public static fragment_one instance = null;

    public fragment_one() {

    }

    @SuppressLint("ValidFragment")
    public fragment_one(String DATE) {
        this.DATE = DATE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_infor_one, container, false);
        instance = this;
        init(v);
        API_2();
        return v;
    }

    void init(View v) {
        viewPager = (CustomViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        customTabLayout(tabLayout);
        int mg_10 = MyApplication.getInstance().getDimens(getActivity(), R.dimen.tablayoutmargin10);
        int mg_5 = MyApplication.getInstance().getDimens(getActivity(), R.dimen.tablayoutmargin5);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            if (i == 0) {
                p.setMargins(mg_10, 0, mg_5, 0);
            } else if (i == 1) {
                p.setMargins(mg_5, 0, mg_5, 0);
            } else if (i == 2) {
                p.setMargins(mg_5, 0, mg_5, 0);
            } else if (i == 3) {
                p.setMargins(mg_5, 0, mg_5, 0);
            } else if (i == 4) {
                p.setMargins(mg_5, 0, mg_10, 0);
            }
            tab.requestLayout();
        }
    }

    private List<OneNew> arrayList = new ArrayList<>();
    private List<OneNew> arrayList2 = new ArrayList<>();
    private List<OneNew> arrayList3 = new ArrayList<>();
    private List<OneNew> arrayList4 = new ArrayList<>();
    private List<OneNew> arrayList5 = new ArrayList<>();
    public boolean flag = true;
    List<String> listIdNews = new ArrayList<>();

    boolean unRead(String id) {
        if (listIdNews.size() > 0) {
            for (String obj : listIdNews) {
                if (obj.trim().equals(id.trim()))
                    return false;
            }
            return true;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        PrefManager pref = new PrefManager(getActivity());
        boolean flag = pref.getUPDATE_NEWS();
        if (flag) {
            pref.setUPDATE_NEWS(false);
            String data = pref.getID_NEWS();
            if (data.length() > 0) {
                Type listType = new TypeToken<List<String>>() {
                }.getType();
                listIdNews = new Gson().fromJson(data, listType);
                for (OneNew obj : arrayList) {
                    obj.setFlag(unRead(obj.getV3().trim()));
                }
                for (OneNew obj : arrayList2) {
                    obj.setFlag(unRead(obj.getV3().trim()));
                }
                for (OneNew obj : arrayList3) {
                    obj.setFlag(unRead(obj.getV3().trim()));
                }
                for (OneNew obj : arrayList4) {
                    obj.setFlag(unRead(obj.getV3().trim()));
                }
                for (OneNew obj : arrayList5) {
                    obj.setFlag(unRead(obj.getV3().trim()));
                }
                one_new.instance.updateList(arrayList);
                two_new.instance.updateList(arrayList2);
                three_new.instance.updateList(arrayList3);
                four_new.instance.updateList(arrayList4);
                five_new.instance.updateList(arrayList5);
            }
        }


    }

    public void API_2() {
        Log.d(TAG, "API_2");
        if (flag) {
            flag = false;
            new HttpRequest(getActivity()).new API_2(MainActivity.dataLogin, new IF_2() {
                @Override
                public void onSuccess(String s) {
                    flag = true;
                    arrayList.clear();
                    arrayList2.clear();
                    arrayList3.clear();
                    arrayList4.clear();
                    arrayList5.clear();
                    Log.d(TAG, "s:" + s);
                    if (s != null && s.length() > 0) {
                        ListOneNews listOneNews = new Gson().fromJson(s, ListOneNews.class);
                        if (listOneNews.getRESULT() == 0) {
                            List<OneNew> LIST = listOneNews.getLIST();
                            if (LIST != null && LIST.size() > 0) {
                                for (int i = 0; i < LIST.size(); i++) {

                                    OneNew ob = LIST.get(i);
                                    Log.d(TAG, "V3:" + ob.getV3()+" V4:"+ob.getV4());
                                    String V1 = ob.getV1();
                                    if (V1.equals("01")) {
                                        arrayList.add(ob);
                                    } else if (V1.equals("02")) {
                                        arrayList2.add(ob);
                                    } else if (V1.equals("03")) {
                                        arrayList3.add(ob);
                                    } else if (V1.equals("04")) {
                                        arrayList4.add(ob);
                                    } else if (V1.equals("05")) {
                                        arrayList5.add(ob);
                                    }
                                }
                                PrefManager pref = new PrefManager(getActivity());
                                String data = pref.getID_NEWS();
                                if(data.length()>0)
                                {
                                    Type listType = new TypeToken<List<String>>() {
                                    }.getType();
                                    listIdNews = new Gson().fromJson(data, listType);
                                }

                                for (OneNew obj : arrayList) {
                                    obj.setFlag(unRead(obj.getV3().trim()));
                                }
                                for (OneNew obj : arrayList2) {
                                    obj.setFlag(unRead(obj.getV3().trim()));
                                }
                                for (OneNew obj : arrayList3) {
                                    obj.setFlag(unRead(obj.getV3().trim()));
                                }
                                for (OneNew obj : arrayList4) {
                                    obj.setFlag(unRead(obj.getV3().trim()));
                                }
                                for (OneNew obj : arrayList5) {
                                    obj.setFlag(unRead(obj.getV3().trim()));
                                }
                                one_new.instance.updateList(arrayList);
                                two_new.instance.updateList(arrayList2);
                                three_new.instance.updateList(arrayList3);
                                four_new.instance.updateList(arrayList4);
                                five_new.instance.updateList(arrayList5);

                            } else {
                                // nodata
                                one_new.instance.updateList(arrayList);
                                two_new.instance.updateList(arrayList2);
                                three_new.instance.updateList(arrayList3);
                                four_new.instance.updateList(arrayList4);
                                five_new.instance.updateList(arrayList5);
                            }
                        } else {
                            // null data
                            one_new.instance.updateList(arrayList);
                            two_new.instance.updateList(arrayList2);
                            three_new.instance.updateList(arrayList3);
                            four_new.instance.updateList(arrayList4);
                            five_new.instance.updateList(arrayList5);
                        }
                    } else {
                        one_new.instance.updateList(arrayList);
                        two_new.instance.updateList(arrayList2);
                        three_new.instance.updateList(arrayList3);
                        four_new.instance.updateList(arrayList4);
                        five_new.instance.updateList(arrayList5);
                    }
                }
            }).execute();
        }

    }

    private void setupViewPager(CustomViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new one_new(), "");
        adapter.addFragment(new two_new(), "");
        adapter.addFragment(new three_new(), "");
        adapter.addFragment(new four_new(), "");
        adapter.addFragment(new five_new(), "");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void customTabLayout(TabLayout tabLayout) {
        View tabFive = LayoutInflater.from(getActivity()).inflate(R.layout.tab_infor, null);
        TextView tvTitle_5 = (TextView) tabFive.findViewById(R.id.tvTitle);
        tvTitle_5.setText(getResources().getString(R.string.product));
        tabLayout.getTabAt(4).setCustomView(tabFive);

        View tabFour = LayoutInflater.from(getActivity()).inflate(R.layout.tab_infor, null);
        TextView tvTitle_4 = (TextView) tabFour.findViewById(R.id.tvTitle);
        tvTitle_4.setText(getResources().getString(R.string.display));
        tabLayout.getTabAt(3).setCustomView(tabFour);

        View tabThree = LayoutInflater.from(getActivity()).inflate(R.layout.tab_infor, null);
        TextView tvTitle_3 = (TextView) tabThree.findViewById(R.id.tvTitle);
        tvTitle_3.setText(getResources().getString(R.string.Contest));
        tabLayout.getTabAt(2).setCustomView(tabThree);

        View tabTwo = LayoutInflater.from(getActivity()).inflate(R.layout.tab_infor, null);
        TextView tvTitle_2 = (TextView) tabTwo.findViewById(R.id.tvTitle);
        tvTitle_2.setText(getResources().getString(R.string.promotion));
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        View tabOne = LayoutInflater.from(getActivity()).inflate(R.layout.tab_infor, null);
        TextView tvTitle = (TextView) tabOne.findViewById(R.id.tvTitle);
        tvTitle.setText(getResources().getString(R.string.News));
        tabLayout.getTabAt(0).setCustomView(tabOne);
    }
}