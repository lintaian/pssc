package com.lps.pssc.util;

public enum DbMap {
	StudentRecord("rec_student_record"),
	ClassDict("inf_class_dict"),
	GradeDict("inf_grade_dict"),
	Student("inf_student"),
	SubjectDict("inf_subject_dict"),
	Teacher("inf_teacher"),
	TeacherClassDict("inf_teacher_class_dict"),
	Courseware("res_courseware"),
	CoursewareDict("res_courseware_dict"),
	Exercise("res_exercise"),
	ExercisePackage("res_exercise_package"),
	ExerciseDict("res_exercise_dict"),
	Picture("res_picture"),
	PicturePackage("res_picture_package"),
	PictureDict("res_picture_dict"),
	PictureExerciseDict("res_picture_exercise_dict"),
	VideoPackage("res_video_package"),
	VideoDict("res_video_dict"),
	Answer("rec_answer"),
	AnswerLog("rec_answer_log"),
	PictureTrace("rec_picture_trace"),
	PreemptiveResponse ("rec_preemptive_response "),
	Class("rt_class"),
	ClassOperate("rt_class_operate");
	private String context;
	private DbMap(String context) {
		this.context = context;
	}
	public String getContext() {
		return this.context;
	}
}
