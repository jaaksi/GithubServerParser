# GithubServerParser
Github服务器自定义GsonAdapter数据解析
下面是Github
https://api.github.com/repos/jaaksi/API/contents/contact/config/config.json

其中content对应的内容才是你自己的数据。而且这个内容是经过Base64编码的（注意里里面有换行符\n）。
这样的数据，如果使用当今流行的retrofit，那么定义结构体的时候就费事了。
我们需要定义一个外层的module，然后得到之后获取content内容，然后转码，最后再解析，得到自己真正想要的实体。多么的不方便啊。
事实上我们并不需要关心Github服务器返回我们的外面这层数据，
我们只想直接得到我们真正的数据。那么这个时候利用自定义的GsonAdapterFactory就可以十分方便的实现。

{
"name": "config.json",
"path": "contact/config/config.json",
"sha": "e580ad18b35d0f70aa9bb78caad181241889db2e",
"size": 481,
"url": "https://api.github.com/repos/jaaksi/API/contents/contact/config/config.json?ref=master",
"html_url": "https://github.com/jaaksi/API/blob/master/contact/config/config.json",
"git_url": "https://api.github.com/repos/jaaksi/API/git/blobs/e580ad18b35d0f70aa9bb78caad181241889db2e",
"download_url": "https://raw.githubusercontent.com/jaaksi/API/master/contact/config/config.json",
"type": "file",
"content": "ewogICJlcnJubyI6IDAsCiAgImVycm9yIjogIiIsCiAgImRhdGEiOiB7CiAg\nICAidGFiIjogWwogICAgICB7CiAgICAgICAgInRpdGxlIjogIua2iOaBryIs\nCiAgICAgICAgImltZ1VybCI6ICJjb250YWN0Oi8vdGFiL21lc3NhZ2UiLAog\nICAgICAgICJhY3Rpb25VcmwiOiAiY29udGFjdDovL3RhYi9tZXNzYWdlIgog\nICAgICB9LAogICAgICB7CiAgICAgICAgInRpdGxlIjogIuiBlOezu+S6uiIs\nCiAgICAgICAgImltZ1VybCI6ICJjb250YWN0Oi8vdGFiL2NvbnRhY3RzIiwK\nICAgICAgICAiYWN0aW9uVXJsIjogImNvbnRhY3Q6Ly90YWIvY29udGFjdHMi\nCiAgICAgIH0sCiAgICAgIHsKICAgICAgICAidGl0bGUiOiAi5oiR55qEIiwK\nICAgICAgICAiaW1nVXJsIjogImh0dHA6Ly9wLjM3NjEuY29tL3BpYy8yMzE0\nMzIxNjk1NzUuanBnIiwKICAgICAgICAiYWN0aW9uVXJsIjogImNvbnRhY3Q6\nLy90YWIvbWluZSIKICAgICAgfQogICAgXQogIH0KfQ==\n",
"encoding": "base64",
"_links": {
"self": "https://api.github.com/repos/jaaksi/API/contents/contact/config/config.json?ref=master",
"git": "https://api.github.com/repos/jaaksi/API/git/blobs/e580ad18b35d0f70aa9bb78caad181241889db2e",
"html": "https://github.com/jaaksi/API/blob/master/contact/config/config.json"
}
}
