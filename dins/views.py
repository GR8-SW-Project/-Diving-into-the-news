# from rest_framework import viewsets
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.throttling import AnonRateThrottle
from dins.serializers import NewsSerializer, KeywordsSerializer, KeywordsWeekSerializer, KeywordsMonthSerializer
from dins.models import News, Keywords, KeywordsWeek, KeywordsMonth

# Create your views here.
class NewsViewSet(APIView):
    # throttle_classes = [AnonRateThrottle]
    
    def get(self, request, **kwargs):
        search_date_start = request.GET.get('date_start') # news?date_start=2022-09-19
        search_date_end = request.GET.get('date_end')
        search_category = request.GET.get('category')
        search_content = request.GET.get('content')
        
        if search_date_start != None and search_date_end != None and search_category != None and search_content != None:
            queryset = News.objects.filter(date__range=[search_date_start, search_date_end], category=search_category, content__contains=search_content)
        elif search_date_start != None and search_date_end != None and search_category != None:
            queryset = News.objects.filter(date__range=[search_date_start, search_date_end], category=search_category)
        else:
            queryset = News.objects.all()
        
        serializer = NewsSerializer(queryset, many=True)
        return Response(serializer.data)
    
# http://127.0.0.1:8000/news?date=2022-09-13&category=사회content=마이크로스프트
class KeywordsViewSet(APIView):
    # throttle_classes = [AnonRateThrottle]
    
    def get(self, request, **kwargs):
        search_date = request.GET.get('date')
        search_category = request.GET.get('category')
        
        if search_date != None and search_category != None:
            queryset = Keywords.objects.filter(date=search_date, category=search_category)
            # queryset = Keywords.objects.filter(date=search_date, category=search_category).values('keyword', 'importance')
        else:
            queryset = Keywords.objects.all()
            
        serializer = KeywordsSerializer(queryset, many=True)
        return Response(serializer.data)
        # return Response(queryset)

class KeywordsWeekViewSet(APIView):
    # throttle_classes = [AnonRateThrottle]
    
    def get(self, request, **kwargs):
        search_date_start = request.GET.get('date_start')
        search_date_end = request.GET.get('date_end')
        search_category = request.GET.get('category')
        
        if search_date_start != None and search_date_end != None and search_category != None:
            queryset = KeywordsWeek.objects.filter(date_start=search_date_start, date_end=search_date_end, category=search_category)
            # queryset = Keywords.objects.filter(date=search_date, category=search_category).values('keyword', 'importance')
        else:
            queryset = KeywordsWeek.objects.all()
            
        serializer = KeywordsWeekSerializer(queryset, many=True)
        return Response(serializer.data)
        # return Response(queryset)

class KeywordsMonthViewSet(APIView):
    # throttle_classes = [AnonRateThrottle]
    
    def get(self, request, **kwargs):
        search_month = request.GET.get('month')
        search_category = request.GET.get('category')
        
        if search_month != None and search_category != None:
            queryset = KeywordsMonth.objects.filter(month=search_month, category=search_category)
            # queryset = Keywords.objects.filter(date=search_date, category=search_category).values('keyword', 'importance')
        else:
            queryset = KeywordsMonth.objects.all()
            
        serializer = KeywordsMonthSerializer(queryset, many=True)
        return Response(serializer.data)
        # return Response(queryset)

# queryset = News.objects.filter(field_id__lt=6)
# queryset = News.objects.filter(field_id=1) | News.objects.filter(field_id=2)
# queryset = News.objects.filter(date_news__month='08')