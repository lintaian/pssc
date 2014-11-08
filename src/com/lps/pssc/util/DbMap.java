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
	ExerciseBatch("res_exercise_batch"),
	ExerciseDict("res_exercise_dict"),
	Picture("res_picture"),
	PictureBatch("res_picture_batch"),
	PictureDict("res_picture_dict"),
	PictureExerciseDict("res_picture_exercise_dict"),
	VideoBatch("res_video_batch"),
	VideoDict("res_video_dict"),
	Answer("rec_answer"),
	AnswerLog("rec_answer_log"),
	PictureTrace("rec_picture_trace"),
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
