from rest_framework import serializers
from dins.models import News, Keywords

class NewsSerializer(serializers.ModelSerializer):
    class Meta:
        model = News
        fields = '__all__'
        
class KeywordsSerializer(serializers.ModelSerializer):
    class Meta:
        model = Keywords
        fields = '__all__'