from django.contrib import admin

# Register your models here.
from dins.models import News, Keywords

admin.site.register(News)
admin.site.register(Keywords)