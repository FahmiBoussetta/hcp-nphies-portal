import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IReferenceIdentifier } from 'app/shared/model/reference-identifier.model';
import { getEntities as getReferenceIdentifiers } from 'app/entities/reference-identifier/reference-identifier.reducer';
import { IClaim } from 'app/shared/model/claim.model';
import { getEntities as getClaims } from 'app/entities/claim/claim.reducer';
import { getEntity, updateEntity, createEntity, reset } from './related.reducer';
import { IRelated } from 'app/shared/model/related.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RelatedUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const referenceIdentifiers = useAppSelector(state => state.referenceIdentifier.entities);
  const claims = useAppSelector(state => state.claim.entities);
  const relatedEntity = useAppSelector(state => state.related.entity);
  const loading = useAppSelector(state => state.related.loading);
  const updating = useAppSelector(state => state.related.updating);
  const updateSuccess = useAppSelector(state => state.related.updateSuccess);

  const handleClose = () => {
    props.history.push('/related');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getReferenceIdentifiers({}));
    dispatch(getClaims({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...relatedEntity,
      ...values,
      claimReference: referenceIdentifiers.find(it => it.id.toString() === values.claimReferenceId.toString()),
      claim: claims.find(it => it.id.toString() === values.claimId.toString()),
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
          ...relatedEntity,
          relationShip: 'Prior',
          claimReferenceId: relatedEntity?.claimReference?.id,
          claimId: relatedEntity?.claim?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="hcpNphiesPortalApp.related.home.createOrEditLabel" data-cy="RelatedCreateUpdateHeading">
            <Translate contentKey="hcpNphiesPortalApp.related.home.createOrEditLabel">Create or edit a Related</Translate>
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
                  id="related-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('hcpNphiesPortalApp.related.relationShip')}
                id="related-relationShip"
                name="relationShip"
                data-cy="relationShip"
                type="select"
              >
                <option value="Prior">{translate('hcpNphiesPortalApp.ClaimRelationshipEnum.Prior')}</option>
                <option value="Associated">{translate('hcpNphiesPortalApp.ClaimRelationshipEnum.Associated')}</option>
                <option value="Extend">{translate('hcpNphiesPortalApp.ClaimRelationshipEnum.Extend')}</option>
              </ValidatedField>
              <ValidatedField
                id="related-claimReference"
                name="claimReferenceId"
                data-cy="claimReference"
                label={translate('hcpNphiesPortalApp.related.claimReference')}
                type="select"
              >
                <option value="" key="0" />
                {referenceIdentifiers
                  ? referenceIdentifiers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="related-claim"
                name="claimId"
                data-cy="claim"
                label={translate('hcpNphiesPortalApp.related.claim')}
                type="select"
              >
                <option value="" key="0" />
                {claims
                  ? claims.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/related" replace color="info">
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

export default RelatedUpdate;
