package com.example.catapp.presentation.explore.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catapp.presentation.components.CatBreedImage
import com.example.catapp.presentation.components.CatDetailSheet
import com.example.catapp.presentation.explore.ViewModel.ExploreViewModel
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreView(
    viewModel: ExploreViewModel
){
    val state by viewModel.catBreedData.collectAsState()

    val expanded = remember {
        mutableStateOf(false)
    }

    val isRefreshing = remember {
        mutableStateOf(false)
    }

    when {
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.e != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("An exception occurred: ${state.e}")
            }
        }

        else -> {
            if (state.data.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No cats here")
                }
            }
            else {
                Scaffold(
                    topBar = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            TopAppBar(
                                title = {
                                    Text("Explore cats by breed")
                                }
                            )
                            SearchBar(
                                inputField = {
                                    SearchBarDefaults.InputField(
                                        query = viewModel.q.value,
                                        onQueryChange = { viewModel.q.value = it },
                                        onSearch = {
                                            expanded.value = true
                                            viewModel.searchCatBreed()
                                        },
                                        expanded = expanded.value,
                                        onExpandedChange = { expanded.value = expanded.value },
                                        placeholder = { Text("Search a cat breed") },
                                    )
                                },
                                expanded = expanded.value,
                                onExpandedChange = { expanded.value = false },
                            ) {
                                val searchState by viewModel.catSearchResponse.collectAsState()

                                when{
                                    searchState.isLoading -> {
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                            CircularProgressIndicator()
                                        }
                                    }

                                    searchState.e != null -> {
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                            Text("An exception occurred: ${searchState.e}")
                                        }
                                    }

                                    viewModel.q.value.isEmpty() -> {
                                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                            Text("Try searching Persian")
                                        }
                                    }

                                    else -> {
                                        if(searchState.data.isEmpty()){
                                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                                Text("No cats found")
                                            }
                                        }
                                        else{
                                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                                items(searchState.data.size){ item ->
                                                    CatBreedImage(searchState.data[item].referenceImageId) { }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    PullToRefreshBox(
                        isRefreshing = isRefreshing.value,
                        modifier = Modifier.padding(innerPadding),
                        onRefresh = {
                            isRefreshing.value = true
                            viewModel.getCatBreeds()
                            isRefreshing.value = false
                        }
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(120.dp),
                            contentPadding = PaddingValues(
                                start = 12.dp,
                                top = 16.dp,
                                end = 12.dp,
                                bottom = 16.dp
                            ),
                            modifier = Modifier.padding(10.dp)
                        ) {
                            items(state.data.size) { item ->
                                CatBreedImage(
                                    state.data[item].referenceImageId
                                ) {
                                    viewModel.showSheet.value = true
                                    viewModel.selectedBreed.value = state.data[item]
                                }
                            }
                        }
                        if (viewModel.showSheet.value && viewModel.selectedBreed.value != null) {
                            ModalBottomSheet(
                                onDismissRequest = {
                                    viewModel.showSheet.value = false
                                },
                            ) {
                                val breed = viewModel.selectedBreed.value;
                                CatDetailSheet(breed!!)
                            }
                        }
                    }
                }
            }
        }
    }
}