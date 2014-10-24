package com.lps.pssc.util;

public enum DbMap {
	Record("rt_record"),
	Class("inf_class_dict"),
	Grade("inf_grade_dict"),
	Student("inf_student"),
	Subject("inf_subject_dict"),
	Teacher("inf_teacher"),
	TeacherClass("inf_teacher_class_dict");
	private String context;
	private DbMap(String context) {
		this.context = context;
	}
	public String getContext() {
		return this.context;
	}
}
