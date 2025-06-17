package com.example.catapp.presentation.explore.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.catapp.presentation.components.CatBreedImage
import com.example.catapp.presentation.components.CatDetailSheet
import com.example.catapp.presentation.explore.ViewModel.ExploreViewModel
import com.example.catapp.presentation.components.CatBreedDialog
import com.example.catapp.presentation.components.CatDetailSearchSheet
import com.example.catapp.presentation.components.CatSearchTile
import com.example.catapp.presentation.components.ErrorState
import com.example.catapp.presentation.components.LoadingState


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

    val showDialog = remember{
        mutableStateOf(false)
    }
    val breedIndex = remember{
        mutableIntStateOf(0)
    }

    when {
        state.isLoading -> {
            LoadingState()
        }

        state.e != null -> {
            ErrorState("An exception occurred: ${state.e}")
        }

        else -> {
            if (state.data.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No cats here")
                }
            }
            else {
                PullToRefreshBox(
                    isRefreshing = isRefreshing.value,
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    onRefresh = {
                        isRefreshing.value = true
                        viewModel.getCatBreeds()
                        isRefreshing.value = false
                    }
                ) {
                    Column {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
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

                                when {
                                    searchState.isLoading -> {
                                        LoadingState()
                                    }

                                    searchState.e != null -> {
                                        ErrorState("Something went wrong")
                                    }

                                    viewModel.q.value.isEmpty() -> {
                                        Box(
                                            modifier = Modifier.fillMaxSize(),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("Try searching Persian")
                                        }
                                    }

                                    else -> {
                                        if (searchState.data.isEmpty()) {
                                            Box(
                                                modifier = Modifier.fillMaxSize(),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text("No cats found")
                                            }
                                        } else {
                                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                                items(searchState.data.size) { item ->
                                                    CatSearchTile(searchState.data[item]) {
                                                        viewModel.showSheet.value = true
                                                        viewModel.selectedSearchBreed.value =
                                                            searchState.data[item]
                                                        viewModel.selectedBreed.value = null
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                        ) {
                            items(state.data.size) { item ->
                                CatBreedImage(
                                    state.data[item],
                                ) {
                                    breedIndex.intValue = item
                                    showDialog.value = true
                                }
                            }
                        }
                    }
                    if (viewModel.showSheet.value && (viewModel.selectedBreed.value != null || viewModel.selectedSearchBreed.value != null)) {
                        ModalBottomSheet(
                            onDismissRequest = {
                                viewModel.showSheet.value = false
                            },
                        ) {
                            if(viewModel.selectedBreed.value != null){
                                CatDetailSheet(viewModel.selectedBreed.value!!)
                            }
                            else if(viewModel.selectedSearchBreed.value != null){
                                CatDetailSearchSheet(viewModel.selectedSearchBreed.value!!)
                            }
                        }
                    }
                }
            }
        }
    }

    if(showDialog.value){
        CatBreedDialog(
            showDialog,
            state,
            breedIndex.intValue
            )
    }
}