import sublime, sublime_plugin
import os
import re
import itertools

ST3 = int(sublime.version()) >= 3000

if ST3:
    from .TodoTask import TaskBase 
else:
    from TodoTask import TaskBase

class OpenTaskCommand(TaskBase):
    def run(self, edit):
        TaskBase.open_task(self, edit)

class CancelTaskCommand(TaskBase):
    def run(self, edit):
        TaskBase.cancel_task(self, edit)

class HandTaskCommand(TaskBase):
    def run(self, edit):
        TaskBase.hand_task(self, edit)

class FinishTaskCommand(TaskBase):
    def run(self, edit):
        TaskBase.done_task(self, edit)
