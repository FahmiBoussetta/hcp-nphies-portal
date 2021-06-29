import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IItem } from 'app/shared/model/item.model';
import { getEntities as getItems } from 'app/entities/item/item.reducer';
import { IDetailItem } from 'app/shared/model/detail-item.model';
import { getEntities as getDetailItems } from 'app/entities/detail-item/detail-item.reducer';
import { ISubDetailItem } from 'app/shared/model/sub-detail-item.model';
import { getEntities as getSubDetailItems } from 'app/entities/sub-detail-item/sub-detail-item.reducer';
import { getEntity, updateEntity, createEntity, reset } from './reference-identifier.reducer';
import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ReferenceIdentifierUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const items = useAppSelector(state => state.item.entities);
  const detailItems = useAppSelector(state => state.detailItem.entities);
  const subDetailItems = useAppSelector(state => state.subDetailItem.entities);
  const referenceIdentifierEntity = useAppSelector(state => state.referenceIdentifier.entity);
  const loading = useAppSelector(state => state.referenceIdentifier.loading);
  const updating = useAppSelector(state => state.referenceIdentifier.updating);
  const updateSuccess = useAppSelector(state => state.referenceIdentifier.updateSuccess);

  const handleClose = () => {
    props.history.push('/reference-identifier');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getItems({}));
    dispatch(getDetailItems({}));
    dispatch(getSubDetailItems({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...referenceIdentifierEntity,
      ...values,
      item: items.find(it => it.id.toString() === values.itemId.toString()),
      detailItem: detailItems.find(it => it.id.toString() === values.detailItemId.toString()),
      subDetailItem: subDetailItems.find(it => it.id.toString() === values.subDetailItemId.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...referenceIdentifierEntity,
          itemId: referenceIdentifierEntity?.item?.id,
          detailItemId: referenceIdentifierEntity?.detailItem?.id,
          subDetailItemId: referenceIdentifierEntity?.subDetailItem?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.referenceIdentifier.home.createOrEditLabel" data-cy="ReferenceIdentifierCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.referenceIdentifier.home.createOrEditLabel">
              Create or edit a ReferenceIdentifier
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="reference-identifier-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.referenceIdentifier.ref')}
                id="reference-identifier-ref"
                name="ref"
                data-cy="ref"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.referenceIdentifier.idValue')}
                id="reference-identifier-idValue"
                name="idValue"
                data-cy="idValue"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.referenceIdentifier.identifier')}
                id="reference-identifier-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
              />
              <ValidatedField
                label={translate('hcpNphiesPortalApp.referenceIdentifier.display')}
                id="reference-identifier-display"
                name="display"
                data-cy="display"
                type="text"
              />
              <ValidatedField
                id="reference-identifier-item"
                name="itemId"
                data-cy="item"
                label={translate('hcpNphiesPortalApp.referenceIdentifier.item')}
                type="select"
              >
                <option value="" key="0" />
                {items
                  ? items.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="reference-identifier-detailItem"
                name="detailItemId"
                data-cy="detailItem"
                label={translate('hcpNphiesPortalApp.referenceIdentifier.detailItem')}
                type="select"
              >
                <option value="" key="0" />
                {detailItems
                  ? detailItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="reference-identifier-subDetailItem"
                name="subDetailItemId"
                data-cy="subDetailItem"
                label={translate('hcpNphiesPortalApp.referenceIdentifier.subDetailItem')}
                type="select"
              >
                <option value="" key="0" />
                {subDetailItems
                  ? subDetailItems.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/reference-identifier" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ReferenceIdentifierUpdate;
