import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICoverageEligibilityRequest, defaultValue } from 'app/shared/model/coverage-eligibility-request.model';

export const ACTION_TYPES = {
  FETCH_COVERAGEELIGIBILITYREQUEST_LIST: 'coverageEligibilityRequest/FETCH_COVERAGEELIGIBILITYREQUEST_LIST',
  FETCH_COVERAGEELIGIBILITYREQUEST: 'coverageEligibilityRequest/FETCH_COVERAGEELIGIBILITYREQUEST',
  CREATE_COVERAGEELIGIBILITYREQUEST: 'coverageEligibilityRequest/CREATE_COVERAGEELIGIBILITYREQUEST',
  UPDATE_COVERAGEELIGIBILITYREQUEST: 'coverageEligibilityRequest/UPDATE_COVERAGEELIGIBILITYREQUEST',
  PARTIAL_UPDATE_COVERAGEELIGIBILITYREQUEST: 'coverageEligibilityRequest/PARTIAL_UPDATE_COVERAGEELIGIBILITYREQUEST',
  DELETE_COVERAGEELIGIBILITYREQUEST: 'coverageEligibilityRequest/DELETE_COVERAGEELIGIBILITYREQUEST',
  RESET: 'coverageEligibilityRequest/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICoverageEligibilityRequest>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CoverageEligibilityRequestState = Readonly<typeof initialState>;

// Reducer

export default (state: CoverageEligibilityRequestState = initialState, action): CoverageEligibilityRequestState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYREQUEST_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_COVERAGEELIGIBILITYREQUEST):
    case REQUEST(ACTION_TYPES.UPDATE_COVERAGEELIGIBILITYREQUEST):
    case REQUEST(ACTION_TYPES.DELETE_COVERAGEELIGIBILITYREQUEST):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_COVERAGEELIGIBILITYREQUEST):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYREQUEST_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYREQUEST):
    case FAILURE(ACTION_TYPES.CREATE_COVERAGEELIGIBILITYREQUEST):
    case FAILURE(ACTION_TYPES.UPDATE_COVERAGEELIGIBILITYREQUEST):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_COVERAGEELIGIBILITYREQUEST):
    case FAILURE(ACTION_TYPES.DELETE_COVERAGEELIGIBILITYREQUEST):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYREQUEST_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_COVERAGEELIGIBILITYREQUEST):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_COVERAGEELIGIBILITYREQUEST):
    case SUCCESS(ACTION_TYPES.UPDATE_COVERAGEELIGIBILITYREQUEST):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_COVERAGEELIGIBILITYREQUEST):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_COVERAGEELIGIBILITYREQUEST):
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

const apiUrl = 'api/coverage-eligibility-requests';

// Actions

export const getEntities: ICrudGetAllAction<ICoverageEligibilityRequest> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_COVERAGEELIGIBILITYREQUEST_LIST,
  payload: axios.get<ICoverageEligibilityRequest>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICoverageEligibilityRequest> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COVERAGEELIGIBILITYREQUEST,
    payload: axios.get<ICoverageEligibilityRequest>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICoverageEligibilityRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COVERAGEELIGIBILITYREQUEST,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICoverageEligibilityRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COVERAGEELIGIBILITYREQUEST,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICoverageEligibilityRequest> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_COVERAGEELIGIBILITYREQUEST,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICoverageEligibilityRequest> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COVERAGEELIGIBILITYREQUEST,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
