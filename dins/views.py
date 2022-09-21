from django.shortcuts import render
from .models import News

# Create your views here.

def news_view(request):
    news_list = News.objects.all()
    return render(request, 'index.html', {'news_list' : news_list})