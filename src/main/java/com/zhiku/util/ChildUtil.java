package com.zhiku.util;

import com.zhiku.entity.mongodb.Child;

import java.util.*;

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
        Map<Integer,Child> map=new HashMap<>();
        //先赋值newChildren，可以覆盖orgChildren
        for(Child child:newChildren){
            map.put( child.getSection_seq(),child );
        }

        for(Child child:orgChildren){
            Child newChild=map.get( child.getSection_seq() );
            if(newChild==null){
                map.put( child.getSection_seq(),child );
            }else if(child.getLevel()==1){//如果是章，到节再覆盖
                newChild.setSub( merge(child.getSub(),newChild.getSub()) );
                if(newChild.getSection_name()==null){//空章(标题为null)可以保留原章标题
                    newChild.setSection_name( child.getSection_name() );
                }
                newChild.setSid( child.getSid() );//原章id可保留，仅章可以
            }
            //非章，不能覆盖newChildren
        }
        List<Child> re=new ArrayList<>(  );
        for (Child child : map.values() ) {
            if (child != null) {
                re.add( child );
            }
        }
        re.sort( Comparator.comparing( Child::getSection_seq ) );

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
                int m=minSid(child.getSub(),level);
                if(m<min){
                    min=m;
                }
            }
        }
        return min;
    }

}
