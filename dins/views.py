from rest_framework import viewsets
# from rest_framework.views import APIView
# from rest_framework.response import Response
from dins.serializers import NewsSerializer, KeywordsSerializer

# from django.shortcuts import render
from dins.models import News, Keywords

# Create your views here.

# def news_view(request):
#     news_list = News.objects.all()
#     return render(request, 'index.json', {'news_list' : news_list})

class NewsViewSet(viewsets.ModelViewSet):
    queryset = News.objects.all()
    # queryset = News.objects.filter(field_id__lt=6)
    # queryset = News.objects.filter(field_id=1) | News.objects.filter(field_id=2)
    # queryset = News.objects.filter(date_news__month='08')
    serializer_class = NewsSerializer

class KeywordsViewSet(viewsets.ModelViewSet):
    queryset = Keywords.objects.all()
    # queryset = News.objects.filter(field_id__lt=6)
    # queryset = News.objects.filter(field_id=1) | News.objects.filter(field_id=2)
    # queryset = News.objects.filter(date_news__month='08')
    serializer_class = KeywordsSerializer

# class NewsListAPI(APIView):
#     def get(self, request):
#         queryset = News.objects.all()
#         serializer_class = NewsSerializer(queryset, many=True)
#         return Response(serializer_class.data)