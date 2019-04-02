# ScrollViewAndTabLayoutDemo

### ScrollView与tabLayout实现滑动关联  与taobao功能相似

思路是以一个集合来维护

主体部分为一个scrollview里面有个LinearLayout
然后子LinearLayout里面有3个业务相关的模块
```
private List<LinearLayout> anchorList = new ArrayList<>();
anchorList.add(llTop);
anchorList.add(llMid);
anchorList.add(llFoot);
```
appbar 为一个 tablayout    
```
//tablayout相关
//根据他得到的位置 滑动到相应的位置
tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //点击标签，使scrollview滑动，isScroll置false
                isScroll = false;
                int pos = tab.getPosition();
                int top = anchorList.get(pos).getTop();
                scrollview.scrollTo(0, top);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
```

通过 private boolean isScroll = false;这个标志位来表示  这次滑动是谁引起的

```
//这里是用来判断  scrollview自己滑动的时候
        scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当由scrollview触发时，isScroll 置true
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isScroll = true;
                }
                return false;
            }
        });
```
  
如果srollview滑动  通过onScrollChanged 获取滑动的距离y

```
scrollview.setCallbacks(new CustomScrollView.Callbacks() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
                if (isScroll) {
                    for (int i = titles.length - 1; i >= 0; i--) {

                        //根据滑动距离，对比各模块距离父布局顶部的高度判断
                        if (y > anchorList.get(i).getTop() - 10) {
                            setScrollPos(i);
                            Log.e("demo", "y=" + y + "===getTop" + anchorList.get(i).getTop() + "postion" + lastPos);
                            break;
                        }
                    }
                }
            }
        });
  //tablayout对应标签的切换
    private void setScrollPos(int newPos) {
        if (lastPos != newPos) {
            //该方法不会触发tablayout 的onTabSelected 监听
            // tab.setScrollPosition(newPos, 0, true);
            tab.setScrollPosition(newPos, 0, true);
        }
        lastPos = newPos;
    }        
```
