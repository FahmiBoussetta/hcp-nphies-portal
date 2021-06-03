import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICoverageEligibilityResponse, defaultValue } from 'app/shared/model/coverage-eligibility-response.model';

export const ACTION_TYPES = {
  FETCH_COVERAGEELIGIBILITYRESPONSE_LIST: 'coverageEligibilityResponse/FETCH_COVERAGEELIGIBILITYRESPONSE_LIST',
  FETCH_COVERAGEELIGIBILITYRESPONSE: 'coverageEligibilityResponse/FETCH_COVERAGEELIGIBILITYRESPONSE',
  CREATE_COVERAGEELIGIBILITYRESPONSE: 'coverageEligibilityResponse/CREATE_COVERAGEELIGIBILITYRESPONSE',
  UPDATE_COVERAGEELIGIBILITYRESPONSE: 'coverageEligibilityResponse/UPDATE_COVERAGEELIGIBILITYRESPONSE',
  PARTIAL_UPDATE_COVERAGEELIGIBILITYRESPONSE: 'coverageEligibilityResponse/PARTIAL_UPDATE_COVERAGEELIGIBILITYRESPONSE',
  DELETE_COVERAGEELIGIBILITYRESPONSE: 'coverageEligibilityResponse/DELETE_COVERAGEELIGIBILITYRESPONSE',
  RESET: 'coverageEligibilityResponse/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICoverageEligibilityResponse>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CoverageEligibilityResponseState = Readonly<typeof initialState>;

// Reducer

export default (state: CoverageEligibilityResponseState = initialState, action): CoverageEligibilityResponseState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYRESPONSE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYRESPONSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COVERAGEELIGIBILITYRESPONSE):
    case REQUEST(ACTION_TYPES.UPDATE_COVERAGEELIGIBILITYRESPONSE):
    case REQUEST(ACTION_TYPES.DELETE_COVERAGEELIGIBILITYRESPONSE):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COVERAGEELIGIBILITYRESPONSE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYRESPONSE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYRESPONSE):
    case FAILURE(ACTION_TYPES.CREATE_COVERAGEELIGIBILITYRESPONSE):
    case FAILURE(ACTION_TYPES.UPDATE_COVERAGEELIGIBILITYRESPONSE):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COVERAGEELIGIBILITYRESPONSE):
    case FAILURE(ACTION_TYPES.DELETE_COVERAGEELIGIBILITYRESPONSE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYRESPONSE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYRESPONSE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COVERAGEELIGIBILITYRESPONSE):
    case SUCCESS(ACTION_TYPES.UPDATE_COVERAGEELIGIBILITYRESPONSE):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COVERAGEELIGIBILITYRESPONSE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COVERAGEELIGIBILITYRESPONSE):
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

const apiUrl = 'api/coverage-eligibility-responses';

// Actions

export const getEntities: ICrudGetAllAction<ICoverageEligibilityResponse> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COVERAGEELIGIBILITYRESPONSE_LIST,
  payload: axios.get<ICoverageEligibilityResponse>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICoverageEligibilityResponse> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COVERAGEELIGIBILITYRESPONSE,
    payload: axios.get<ICoverageEligibilityResponse>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICoverageEligibilityResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COVERAGEELIGIBILITYRESPONSE,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICoverageEligibilityResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COVERAGEELIGIBILITYRESPONSE,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICoverageEligibilityResponse> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COVERAGEELIGIBILITYRESPONSE,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICoverageEligibilityResponse> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COVERAGEELIGIBILITYRESPONSE,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
