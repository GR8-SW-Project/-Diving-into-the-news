"""config URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.urls import include, path
from rest_framework import routers

from dins.views import NewsViewSet, KeywordsViewSet


# router = routers.DefaultRouter()
# router.register(r'news', NewsViewSet.as_view())

# router2 = routers.DefaultRouter()
# router.register(r'keywords', KeywordsViewSet)

# router = routers.DefaultRouter()
# router.register(r'news', news_view)

urlpatterns = [
    # path('', include(router.urls)),
    # path('', include(router2.urls)),
    path('news', NewsViewSet.as_view()),
    path('keywords', KeywordsViewSet.as_view()),
    # re_path('admin', admin.site.urls),
]
