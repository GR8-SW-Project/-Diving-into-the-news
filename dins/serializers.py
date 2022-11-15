from rest_framework import serializers
from dins.models import News, Keywords, KeywordsWeek, KeywordsMonth

class NewsSerializer(serializers.ModelSerializer):
    class Meta:
        model = News
        fields = '__all__'
        
class KeywordsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Keywords
        fields = '__all__'

class KeywordsWeekSerializer(serializers.ModelSerializer):
    class Meta:
        model = KeywordsWeek
        fields = '__all__'

class KeywordsMonthSerializer(serializers.ModelSerializer):
    class Meta:
        model = KeywordsMonth
        fields = '__all__'