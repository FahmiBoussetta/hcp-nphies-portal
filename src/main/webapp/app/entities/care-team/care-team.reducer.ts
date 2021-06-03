import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICareTeam, defaultValue } from 'app/shared/model/care-team.model';

export const ACTION_TYPES = {
  FETCH_CARETEAM_LIST: 'careTeam/FETCH_CARETEAM_LIST',
  FETCH_CARETEAM: 'careTeam/FETCH_CARETEAM',
  CREATE_CARETEAM: 'careTeam/CREATE_CARETEAM',
  UPDATE_CARETEAM: 'careTeam/UPDATE_CARETEAM',
  PARTIAL_UPDATE_CARETEAM: 'careTeam/PARTIAL_UPDATE_CARETEAM',
  DELETE_CARETEAM: 'careTeam/DELETE_CARETEAM',
  RESET: 'careTeam/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICareTeam>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

export type CareTeamState = Readonly<typeof initialState>;

// Reducer

export default (state: CareTeamState = initialState, action): CareTeamState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CARETEAM_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CARETEAM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CARETEAM):
    case REQUEST(ACTION_TYPES.UPDATE_CARETEAM):
    case REQUEST(ACTION_TYPES.DELETE_CARETEAM):
    case REQUEST(ACTION_TYPES.PARTIAL_UPDATE_CARETEAM):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CARETEAM_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CARETEAM):
    case FAILURE(ACTION_TYPES.CREATE_CARETEAM):
    case FAILURE(ACTION_TYPES.UPDATE_CARETEAM):
    case FAILURE(ACTION_TYPES.PARTIAL_UPDATE_CARETEAM):
    case FAILURE(ACTION_TYPES.DELETE_CARETEAM):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CARETEAM_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CARETEAM):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CARETEAM):
    case SUCCESS(ACTION_TYPES.UPDATE_CARETEAM):
    case SUCCESS(ACTION_TYPES.PARTIAL_UPDATE_CARETEAM):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CARETEAM):
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

const apiUrl = 'api/care-teams';

// Actions

export const getEntities: ICrudGetAllAction<ICareTeam> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CARETEAM_LIST,
  payload: axios.get<ICareTeam>(`${apiUrl}?cacheBuster=${new Date().getTime()}`),
});

export const getEntity: ICrudGetAction<ICareTeam> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CARETEAM,
    payload: axios.get<ICareTeam>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICareTeam> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CARETEAM,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICareTeam> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CARETEAM,
    payload: axios.put(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const partialUpdate: ICrudPutAction<ICareTeam> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.PARTIAL_UPDATE_CARETEAM,
    payload: axios.patch(`${apiUrl}/${entity.id}`, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICareTeam> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CARETEAM,
    payload: axios.delete(requestUrl),
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
