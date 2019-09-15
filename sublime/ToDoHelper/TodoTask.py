import sublime, sublime_plugin
import re
import itertools

# 让其支持字符串形式的tag，而不是字符形式
# 通过字符串替换的形式来实现的
# task_icons = ['[+]', '[→]', '[x]', '[√]']
task_icons = ['☐', '❑', '✘', '✔']
task_actions = {'open':0, 'hand':1, 'cancel':2, 'done':3}

class TaskBase(sublime_plugin.TextCommand):
    def replace_tag(self, line_text, old_str, new_str):
        temp_str = line_text.replace(old_str, new_str)
        if temp_str != line_text:
            return [True, temp_str]
        return [False, '']

    def task_command(self, edit, tag1st, tag2nd):
        regions = itertools.chain(*(reversed(self.view.lines(region)) for region in reversed(list(self.view.sel()))))
        for line in regions:
            line_text = self.view.substr(line)
            brow_test = [(x, task_actions[tag2nd]) for x in range(task_actions[tag1st], task_actions[tag2nd])]
            brow_test.append((task_actions[tag2nd], task_actions[tag1st]))
            print(brow_test)
            for v in brow_test:
                old_str = task_icons[v[0]]
                new_str = task_icons[v[1]]
                result = self.replace_tag(line_text, old_str, new_str)
                if result[0]:
                    self.view.replace(edit, line, result[1])
                    break

    def insert_tag(self, edit, tag, after_tag_string):
        curr_tag = task_icons[task_actions[tag]]

        regions = itertools.chain(*(reversed(self.view.lines(region)) for region in reversed(list(self.view.sel()))))
        for i, line in enumerate(regions):
            not_empty_line = re.match('^(\s*)(\S.*)$', self.view.substr(line))
            empty_line     = re.match('^(\s+)$', self.view.substr(line))
            if not_empty_line:
                grps = not_empty_line.groups()
                line_text = grps[0] + curr_tag + after_tag_string + grps[1]
            elif empty_line:
                grps = empty_line.groups()
                line_text = grps[0] + curr_tag + after_tag_string
            else:
                line_text = curr_tag + after_tag_string
            self.view.replace(edit, line, line_text)

    def open_task(self, edit):
        self.insert_tag(edit, 'open', ' ')

    def cancel_task(self, edit):
        tag1st = 'open'
        tag2nd = 'cancel'
        self.task_command(edit, tag1st, tag2nd)

    def hand_task(self, edit):
        tag1st = 'open'
        tag2nd = 'hand'
        self.task_command(edit, tag1st, tag2nd)

    def done_task(self, edit):
        tag1st = 'open'
        tag2nd = 'done'
        self.task_command(edit, tag1st, tag2nd)
