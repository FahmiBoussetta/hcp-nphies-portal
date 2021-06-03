import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICoverage, defaultValue } from 'app/shared/model/coverage.model';

export const ACTION_TYPES = {
  FETCH_COVERAGE_LIST: 'coverage/FETCH_COVERAGE_LIST',
  FETCH_COVERAGE: 'coverage/FETCH_COVERAGE',
  CREATE_COVERAGE: 'coverage/CREATE_COVERAGE',
  UPDATE_COVERAGE: 'coverage/UPDATE_COVERAGE',
  PARTIAL_UPDATE_COVERAGE: 'coverage/PARTIAL_UPDATE_COVERAGE',
  DELETE_COVERAGE: 'coverage/DELETE_COVERAGE',
  RESET: 'coverage/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICoverage>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CoverageState = Readonly<typeof initialState>;

// Reducer

export default (state: CoverageState = initialState, action): CoverageState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COVERAGE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COVERAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COVERAGE):
    case REQUEST(ACTION_TYPES.UPDATE_COVERAGE):
    case REQUEST(ACTION_TYPES.DELETE_COVERAGE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COVERAGE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COVERAGE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COVERAGE):
    case FAILURE(ACTION_TYPES.CREATE_COVERAGE):
    case FAILURE(ACTION_TYPES.UPDATE_COVERAGE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COVERAGE):
    case FAILURE(ACTION_TYPES.DELETE_COVERAGE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVERAGE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVERAGE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COVERAGE):
    case SUCCESS(ACTION_TYPES.UPDATE_COVERAGE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COVERAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COVERAGE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'api/coverages';

// Actions

export const getEntities: ICrudGetAllAction<ICoverage> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COVERAGE_LIST,
  payload: axios.get<ICoverage>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICoverage> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COVERAGE,
    payload: axios.get<ICoverage>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICoverage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COVERAGE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICoverage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COVERAGE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICoverage> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COVERAGE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICoverage> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COVERAGE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
