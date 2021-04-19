package com.xindog.algorithm;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReverseLinkList {

    public static void main(String[] args) {
        LinkList list1 = new LinkList(1);
        LinkList list2 = new LinkList(2);
        LinkList list3 = new LinkList(3);
        LinkList list4 = new LinkList(4);
        LinkList list5 = new LinkList(5);
        LinkList list6 = new LinkList(6);
        LinkList list7 = new LinkList(7);
        LinkList list8 = new LinkList(8);

        list1.setNext(list2);
        list2.setNext(list3);
        list3.setNext(list4);
        list4.setNext(list5);
        list5.setNext(list6);
        list6.setNext(list7);
        list7.setNext(list8);


        recursiveReverse(list1);
    }


    public static void recursiveReverse(LinkList list) {
        if (list.getNext() == null) {
            log.info("Reverse Call => LinkList={}", list.getValue());
            log.warn("Here I get the value => {}", list.getValue());
            return;
        }
        log.info("Reverse Call => LinkList={}", list.getValue());
        recursiveReverse(list.getNext());
        log.warn("Here I get the value => {}", list.getValue());
    }
}


class LinkList {

    LinkList next;
    Integer value;

    public LinkList(Integer value) {
        this.value = value;
    }

    public LinkList getNext() {
        return next;
    }

    public void setNext(LinkList next) {
        this.next = next;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
