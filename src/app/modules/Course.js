/**
 *Course类，封装了课程列表和知识见解页面的数据
 */
import Vue from "vue";
import * as knowledge from "../apis/knowledgeApi";
import store from "../../store/store";
import {markdown2Html} from "./functions";

export default class Course {
    // #courseId;
    // #courseView;
    // #sectionView;
    // #side;
    // #csdn;
    constructor(courseId = -1) {
        this._courseId = courseId;
        this._courseView = {};
        this._sectionView = {};
        this._side = {
            preSection: "",
            nextSection: ""
        };
        this._csdn = {};
        this._noteViews = [];
        this._colParas = [];
    }

    init() {
        this.getCourseView()
            .then(sid => {
                this.setSectionView(sid);
                this.setCsdn(sid);
            })
            .catch(error => new Vue().$message.warning(error));
    }

    // getCourseList() {
    //     return knowledge.queryCourseList();
    // }

    getCourseView() {
        let params = {cid: this._courseId}
        return knowledge
            .queryCourseView(params, response => {
                this._courseView = response.data.courseView
                if (this._courseView.sections.length > 0)
                    return this._courseView.sections[0].sub[0].sid;
                else throw new Error("章节为空")
            })
    }

    setSectionView(sid) {
        knowledge.querySectionView({sid: sid}, response => {
            this._sectionView = markdown2Html(response.data.sectionView);
            this.setSideSection(sid);
        })
    }

    setCsdn(sid) {
        let params = {
            cid: store.state.course.courseId,
            sid: sid,
            vid: store.state.course.versionId
        };
        knowledge.queryCsdn(params, response => this._csdn = response.data.csdn)
    }

    getNoteView(sid) {
        return knowledge
            .queryNoteView({
                uid: 0,
                sid: sid
            }, response => response.data.noteViews)
    }

    getColParas(sid) {
        return knowledge
            .queryColParas({
                uid: 0,
                sid: sid
            }, response => response.data.colParagraphList)
    }

    setSideSection(sid) {
        let sections = this._courseView.sections;
        let sideIndex = getSideSectionIndex(sid, sections);

        this._side.preSection = findPre(sections, sideIndex);
        this._side.nextSection = findNext(sections, sideIndex);
    }

    setNoteView(sid) {
        this.getNoteView(sid).then(noteViews => (this._noteViews = noteViews));
    }

    setColParas(sid) {
        this.getColParas(sid).then(colParas => (this._colParas = colParas));
    }

    get courseView() {
        return this._courseView;
    }

    get sectionView() {
        return this._sectionView;
    }

    get csdn() {
        return this._csdn;
    }

    get side() {
        return this._side;
    }

    get noteViews() {
        return this._noteViews;
    }

    get colParas() {
        return this._colParas;
    }
}

// 获取当前章节所在的位置
function getSideSectionIndex(sid, sections) {
    let i, j;
    for (i = 0; i < sections.length; i++) {
        for (j = 0; j < sections[i].sub.length; j++) {
            if (sections[i].sub[j].sid === sid) {
                return {i: i, j: j};
            }
        }
    }
    return {i: i, j: j};
}

function findPre(sections, sideIndex) {
    let result;
    let i = sideIndex.i;
    let j = sideIndex.j;

    //找前一个
    if (j !== 0) {
        result = sections[i].sub[j - 1];
    } else {
        if (i !== 0) {
            result = sections[i - 1].sub[sections[i - 1].sub.length - 1];
        } else {
            result = "";
        }
    }
    return result;
}

function findNext(sections, sideIndex) {
    let result;
    let i = sideIndex.i;
    let j = sideIndex.j;

    //找后一个
    if (j !== sections[i].sub.length - 1) {
        result = sections[i].sub[j + 1];
    } else {
        if (i !== sections.length - 1) {
            result = sections[i + 1].sub[0];
        } else {
            result = "";
        }
    }
    return result;
}
