from django.urls import include, path


app_name = 'dins'
urlpatterns = [
    path('', include('rest_framework.urls', namespace='rest_framework_category')),
]
