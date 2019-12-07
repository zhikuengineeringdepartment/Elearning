package com.zhiku.util;

import com.zhiku.entity.mongodb.Child;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChildUtil {

    /*
    按seq从小到大递归排序，
    注：要确保都有seq
     */
    public static void childSort(List<Child> children){
        if(children==null)
            return;
        Collections.sort( children, new Comparator<Child>() {
            @Override
            public int compare(Child o1, Child o2) {
                return o1.getSection_seq()-o2.getSection_seq();
            }
        });
        for (Child child:children){
            if(child!=null&&child.getSub()!=null){
                childSort( child.getSub() );
            }
        }
    }

    /*
    合并两目录结构,newChildren可以覆盖orgChildren，依据同一父节点下seq唯一
    注:要确保level同层级
     */
    public static List<Child> merge(List<Child> orgChildren,List<Child> newChildren){
        if(newChildren==null){
            return orgChildren;
        }else if(orgChildren==null){
            return newChildren;
        }
        //找出最大seq
        int maxseq=0;
        for(Child child:orgChildren){
            if(child.getSection_seq()>maxseq){
                maxseq=child.getSection_seq();
            }
        }
        for(Child child:newChildren){
            if(child.getSection_seq()>maxseq){
                maxseq=child.getSection_seq();
            }
        }
        //seq小为0,大部分时从0开始，不用省空间
        Child[] children=new Child[maxseq+1];
        //先赋值newChildren，可以覆盖orgChildren
        for(Child child:newChildren){
            children[child.getSection_seq()]=child;
        }
        for(Child child:orgChildren){
            if(children[child.getSection_seq()]==null){
                children[child.getSection_seq()]=child;
            }else if(child.getLevel()==1){//如果是章，到节再覆盖
                children[child.getSection_seq()].setSub(
                        merge(child.getSub(),children[child.getSection_seq()].getSub()));
                if(children[child.getSection_seq()].getSection_name()==null){//空章(标题为null)可以保留原章标题
                    children[child.getSection_seq()].setSection_name( child.getSection_name() );
                }
                children[child.getSection_seq()].setSid( child.getSid() );//原章id可保留，仅章可以
            }//非章，不能覆盖newChildren
        }
        List<Child> re=new ArrayList<>(  );
        for (Child child : children) {
            if (child != null) {
                re.add( child );
            }
        }
        return re;
    }

    /*
    获得level层的最大sid
     */
    public static int maxSid(List<Child> children,int level){
        int max=Integer.MIN_VALUE;
        for(Child child:children){
            if(child.getLevel()==level){
                if(max<child.getSid()){
                    max=child.getSid();
                }
            }else if(child.getLevel()<level&&child.getSub()!=null){
                int m=maxSid(child.getSub(),level);
                if(max<m){
                    max=m;
                }
            }
        }
        return max;
    }
    /*
    获得level层的最小sid
    */
    public static int minSid(List<Child> children,int level){
        int min=Integer.MAX_VALUE;
        for(Child child:children){
            if(child.getLevel()==level){
                if(child.getSid()<min){
                    min=child.getSid();
                }
            }else if(child.getLevel()<level&&child.getSub()!=null){
                int m=maxSid(child.getSub(),level);
                if(m<min){
                    min=m;
                }
            }
        }
        return min;
    }

}
