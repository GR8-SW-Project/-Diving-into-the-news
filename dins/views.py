from unicodedata import category
from rest_framework import viewsets
from rest_framework.views import APIView
from rest_framework.response import Response
from dins.serializers import NewsSerializer, KeywordsSerializer
from dins.models import News, Keywords

# Create your views here.
class NewsViewSet(APIView):
    def get(self, request, **kwargs):
        search_date = request.GET.get('date') # news?date=2022-09-19
        search_category = request.GET.get('category')
        search_content = request.GET.get('content')
        
        if search_date != None and search_category != None and search_content != None:
            queryset = News.objects.filter(date=search_date, category=search_category, content__contains=search_content)
        else:
            queryset = News.objects.all()
        
        serializer = NewsSerializer(queryset, many=True)
        return Response(serializer.data)
    
# http://127.0.0.1:8000/news?date=2022-09-13&category=사회content=마이크로스프트
class KeywordsViewSet(APIView):
    def get(self, request, **kwargs):
        search_date = request.GET.get('date')
        
        if search_date:
            queryset = Keywords.objects.filter(date=search_date)
        else:
            queryset = Keywords.objects.all()
            
        serializer = KeywordsSerializer(queryset, many=True)
        return Response(serializer.data)


# queryset = News.objects.filter(field_id__lt=6)
# queryset = News.objects.filter(field_id=1) | News.objects.filter(field_id=2)
# queryset = News.objects.filter(date_news__month='08')