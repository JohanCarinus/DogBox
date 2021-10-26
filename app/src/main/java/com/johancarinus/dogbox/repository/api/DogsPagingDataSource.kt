package com.johancarinus.dogbox.repository.api

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException
import java.net.URISyntaxException

class DogsPagingDataSource(private val api: DogApi) : PagingSource<Int, Uri>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Uri> {
        val pageIndex = params.key ?: 0
        return try {
            val response = api.getRandomImages(params.loadSize)
            val imageUris = mutableListOf<Uri>()
            for (stringUri in response.message) {
                imageUris.add(Uri.parse(stringUri))
            }
            LoadResult.Page(
                data = imageUris,
                prevKey = pageIndex,
                nextKey = pageIndex + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: URISyntaxException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Uri>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}